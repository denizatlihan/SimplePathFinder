package cankaya.ie552.denizatlihan.pso;

import java.awt.Color;
import java.awt.Graphics2D;

import cankaya.ie552.denizatlihan.utility.CoordinateVector;
import cankaya.ie552.denizatlihan.utility.History;

public class PsoResult {

    public Particle arrivedParticle;
    public Long realElapsedTime;
    public boolean solutionFound;
    public long iteration;

    public PsoResult(IterationResult result, long realElapsedTime, long iteration) {

        this.arrivedParticle = result.particle;
        this.solutionFound = result.found;
        this.realElapsedTime = realElapsedTime;
        this.iteration = iteration;
    }

    public void print() {

        if (arrivedParticle != null) {

            System.out.println(
                    "PSO Result -> Iteration: " + iteration + ", Elapsed Time: " + (realElapsedTime / 1000000));
        } else {

            System.out.println(
                    "PSO Result -> SOLUTION NOT FOUND!  Iteration: " + iteration + ", Elapsed Time: "
                            + (realElapsedTime / 1000000));
        }
    }

    public void draw(Graphics2D g) {

        double distance = 0;
        History<CoordinateVector> history = arrivedParticle.getHistory();
        CoordinateVector current = arrivedParticle.getCoordinateVector();

        for (int i = 0; i < history.size() - 1; i++) {

            CoordinateVector c1 = history.get(i);
            CoordinateVector c2 = history.get(i + 1);

            distance += c2.distanceBetween(c1);

            if (solutionFound == false) {

                g.setColor(Color.red);
            } else {
                g.setColor(Color.cyan);
            }
            g.drawLine(c1.x, c1.y, c2.x, c2.y);

            g.setColor(arrivedParticle.getColorOut());
            g.fillOval(c2.x - 3, c2.y - 3, 6, 6);
        }

        CoordinateVector c1 = history.get(history.size() - 1);

        g.setColor(Color.cyan);
        g.drawLine(c1.x, c1.y, current.x, current.y);

        g.setColor(arrivedParticle.getColorOut());
        g.fillOval(current.x - 3, current.y - 3, 6, 6);
        distance += current.distanceBetween(c1);
        System.out.println(String.format("PSO Distance: %.1f", distance));

        g.setColor(Color.white);
        g.drawString("Iteration: " + iteration, 10, 450);
        g.drawString("Elapsed(ms): " + (realElapsedTime / 1000000), 10, 470);

        if (solutionFound == true) {

            g.drawString(String.format("Distance: %.2f", distance), 10, 490);
        } else {

            g.setColor(Color.red);
            g.drawString("Solution Not Found!", 10, 490);

        }

    }

}
