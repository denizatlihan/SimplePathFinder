package cankaya.ie552.denizatlihan.pso;

import java.util.List;

import cankaya.ie552.denizatlihan.Result;
import cankaya.ie552.denizatlihan.TestMedia;
import cankaya.ie552.denizatlihan.drawer.Checkpoint;
import cankaya.ie552.denizatlihan.drawer.IObstacle;

public class PsoSolver {

    private List<IObstacle> obstacles;
    private Checkpoint finish;
    private Swarm swarm;
    private TestMedia media;

    public PsoSolver(TestMedia media, Checkpoint start, Checkpoint finish, List<IObstacle> obstacles,
            int numberOfParticles) {

        this.media = media;
        this.obstacles = obstacles;
        this.finish = finish;
        this.swarm = new Swarm(numberOfParticles, start.getCenterX(), start.getCenterY());

        media.setParticles(swarm.getParticles());
    }

    public Result solve(long iterationLimit, long delayForAnimation) {

        Particle arrivedParticle = null;
        Long realElapsedTime = 0l;
        long iteration = 0;

        while (arrivedParticle == null && iteration < iterationLimit) {

            iteration++;

            long now = System.currentTimeMillis();

            arrivedParticle = swarm.iterate(obstacles, finish);

            realElapsedTime += (System.currentTimeMillis() - now);

            try {

                Thread.sleep(delayForAnimation);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }

            media.repaint();
        }

        return new Result(arrivedParticle, iteration, realElapsedTime);
    }

}
