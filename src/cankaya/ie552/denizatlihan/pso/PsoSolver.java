package cankaya.ie552.denizatlihan.pso;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import cankaya.ie552.denizatlihan.TestMedia;
import cankaya.ie552.denizatlihan.utility.Checkpoint;
import cankaya.ie552.denizatlihan.utility.IObstacle;
import cankaya.ie552.denizatlihan.utility.Utils;

public class PsoSolver {

    private TestMedia media;
    private Checkpoint finish;
    private Swarm swarm;
    private List<IObstacle> obstacles;
    private long iteration = 0;
    private long realElapsedTime = 0l;

    public PsoSolver(TestMedia media, Checkpoint start, Checkpoint finish, List<IObstacle> obstacles,
            int numberOfParticles) {

        this.media = media;
        this.obstacles = obstacles;
        this.finish = finish;

        swarm = new Swarm(numberOfParticles, start.getCenterX(), start.getCenterY());

        media.setDrawer(g2 -> {

            for (Particle p : swarm.getParticles()) {

                p.draw(g2);
            }

            writeIterationSummary(g2);
        });
    }

    public void writeIterationSummary(Graphics2D g2) {

        g2.setColor(Color.white);
        g2.drawString("Iteration: " + iteration, 10, 450);
        g2.drawString("Elapsed(ms): " + (realElapsedTime / 1000000), 10, 470);
    }

    public PsoResult solve(long iterationLimit, long delayForAnimation) {

        Particle arrivedParticle = null;
        boolean solutionFound = false;
        iteration = 0;
        realElapsedTime = 0l;

        while (iteration < iterationLimit) {

            iteration++;

            long start = System.nanoTime();

            arrivedParticle = swarm.iterate(obstacles, finish);

            long now = System.nanoTime();

            long elapsed = (now - start);

            realElapsedTime += (elapsed);

            if (arrivedParticle != null) {

                solutionFound = true;
                break;
            }

            media.repaint();

            Utils.sleep(delayForAnimation);

        }

        return new PsoResult(arrivedParticle, realElapsedTime, solutionFound, iteration);
    }

}
