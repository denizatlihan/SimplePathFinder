package cankaya.ie552.denizatlihan.pso;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import cankaya.ie552.denizatlihan.utility.Checkpoint;
import cankaya.ie552.denizatlihan.utility.CoordinateVector;
import cankaya.ie552.denizatlihan.utility.History;
import cankaya.ie552.denizatlihan.utility.IDrawer;
import cankaya.ie552.denizatlihan.utility.IObstacle;

public class Particle implements IDrawer {

    private int rOut = 8;
    private int rIn = 6;

    private static final int len = 10;
    private static final double maxHeadingChange = Math.PI / 6;

    private Color colorOut = new Color(100, 10, 100);
    private Color colorIn = new Color(100, 100, 250);
    private History<CoordinateVector> history = new History<CoordinateVector>();
    private CoordinateVector current;

    public Particle(int centerX, int centerY, double heading) {

        this.current = new CoordinateVector(centerX, centerY, 0);
    }

    @Override
    public void draw(Graphics g) {

        g.setColor(colorOut);
        g.fillOval(current.x - rOut / 2, current.y - rOut / 2, rOut, rOut);
        g.setColor(colorIn);
        g.fillOval(current.x - rIn / 2, current.y - rIn / 2, rIn, rIn);

        int headingArrowLen = 10;
        int x2 = (int) (headingArrowLen * Math.cos(current.heading));
        int y2 = (int) (headingArrowLen * Math.sin(current.heading));
        g.drawLine(current.x, current.y, current.x + x2, current.y + y2);
    }

    public int calculateNextLocation(List<IObstacle> obstacles, Checkpoint finish, CoordinateVector globalBest) {

        CoordinateVector candidate = calculateRandomPosition(current, finish, globalBest, obstacles, .1, .8, .1);
        boolean available = false;

        while (available == false) {

            available = true;

            for (IObstacle obstacle : obstacles) {

                if (obstacle.contains(candidate)) {

                    for (int i = 0; i < 2; i++) {

                        if (history.size() > 0) {

                            current = history.pop();
                        }
                    }

                    // candidate = calculateRandomPosition(current, finish,
                    // globalBest, obstacles, 1, .0, .0);
                    candidate = rotate(current);

                    available = false;
                    break;
                }
            }
        }

        history.push(new CoordinateVector(current.x, current.y, current.heading));

        current = candidate;

        return finish.distanceTo(current);
    }

    private CoordinateVector rotate(CoordinateVector current) {

        int sign = Math.random() < 0.5 ? -1 : 1;

        double angle = current.heading + sign * maxHeadingChange;

        return new CoordinateVector((int) (current.x + len * Math.cos(angle)),
                (int) (current.y + len * Math.sin(angle)), angle);
    }

    private CoordinateVector calculateRandomPosition(CoordinateVector current, Checkpoint finish,
            CoordinateVector swarmBest, List<IObstacle> obstacles, double cRand, double cGlob, double cSwarm) {

        double angleGlobalBest = Math
                .atan(((double) finish.getCenterY() - current.y) / ((double) finish.getCenterX() - current.x));

        double xGlobalBest = (Math.cos(angleGlobalBest) * len * cGlob);
        double yGlobalBest = (Math.sin(angleGlobalBest) * len * cGlob);

        double angleSwarmBest = Math.atan(((double) swarmBest.y - current.y) / ((double) swarmBest.x - current.x));

        double xSwarmBest = (Math.cos(angleSwarmBest) * len * cSwarm);
        double ySwarmBest = (Math.sin(angleSwarmBest) * len * cSwarm);

        double angleDiff = Math.random() * maxHeadingChange * 2 - maxHeadingChange;

        double xRandom = (Math.cos(current.heading + angleDiff) * len * cRand);
        double yRandom = (Math.sin(current.heading + angleDiff) * len * cRand);

        double xCandidate = current.x + xSwarmBest + xGlobalBest + xRandom;
        double yCandidate = current.y + ySwarmBest + yGlobalBest + yRandom;

        double headingCandidate = Math.atan((yCandidate - current.y) / (xCandidate - current.x));
        double headingDiff = headingCandidate - current.heading;

        if (headingDiff < 0 && headingDiff < -maxHeadingChange) {

            headingDiff = -maxHeadingChange;
            xCandidate = current.x + len * Math.cos(current.heading + headingDiff);
            yCandidate = current.y + len * Math.sin(current.heading + headingDiff);

        } else if (headingDiff > maxHeadingChange) {

            headingDiff = maxHeadingChange;
            xCandidate = current.x + len * Math.cos(current.heading + headingDiff);
            yCandidate = current.y + len * Math.sin(current.heading + headingDiff);
        }

        return new CoordinateVector((int) xCandidate, (int) yCandidate, current.heading + headingDiff);
        /////////////////////////////
        // double randomChange = (Math.random() * maxHeadingChange) -
        // (maxHeadingChange / 2);
        //
        // CoordinateVector candidate = new CoordinateVector(current.x,
        // current.y, current.heading);
        //
        // candidate.x = (int) (current.x + len * c1 * Math.cos(current.heading
        // + randomChange));
        // candidate.y = (int) (current.y + len * c1 * Math.sin(current.heading
        // + randomChange));
        //
        // double angleBetween = Utils.angleBetween(candidate, finish);
        //
        // candidate.x += (int) (len * c2 * Math.cos(angleBetween));
        // candidate.y += (int) (len * c2 * Math.sin(angleBetween));
        //
        // candidate.heading = Utils.angleBetween(current, candidate);

    }

    public boolean inside(Checkpoint finish) {

        return finish.contains(current);
    }

    public void drawHistory(Graphics g) {

        for (int i = 0; i < history.size() - 1; i++) {

            CoordinateVector c1 = history.get(i);
            CoordinateVector c2 = history.get(i + 1);

            if (c2.rotated == true) {

                g.setColor(Color.red);
            } else {
                g.setColor(Color.cyan);
            }
            g.drawLine(c1.x, c1.y, c2.x, c2.y);

            g.setColor(colorOut);
            g.fillOval(c2.x - 3, c2.y - 3, 6, 6);
        }

        CoordinateVector c1 = history.get(history.size() - 1);

        g.setColor(Color.cyan);
        g.drawLine(c1.x, c1.y, current.x, current.y);
    }

    public CoordinateVector getCoordinateVector() {

        return current;
    }

    public History<CoordinateVector> getHistory() {

        return history;
    }

}
