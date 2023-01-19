package cankaya.ie552.denizatlihan.pso;

import java.util.List;

import cankaya.ie552.denizatlihan.TestMedia;
import cankaya.ie552.denizatlihan.utility.Checkpoint;
import cankaya.ie552.denizatlihan.utility.IObstacle;
import cankaya.ie552.denizatlihan.utility.Utils;

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

    public PsoResult solve(long iterationLimit, long delayForAnimation) {

        Particle arrivedParticle = null;
        Long realElapsedTime = 0l;
        long iteration = 0;
        boolean solutionFound = false;

        while (iteration < iterationLimit) {

            iteration++;

            long now = System.currentTimeMillis();

            arrivedParticle = swarm.iterate(obstacles, finish);

            realElapsedTime += (System.currentTimeMillis() - now);

            if (arrivedParticle != null) {

                solutionFound = true;
                break;
            }

            Utils.sleep(delayForAnimation);

            media.repaint();
        }

        return new PsoResult(arrivedParticle, realElapsedTime, solutionFound, iteration);
    }

}
