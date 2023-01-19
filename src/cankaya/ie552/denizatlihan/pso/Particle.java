package cankaya.ie552.denizatlihan.pso;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import cankaya.ie552.denizatlihan.Utility;
import cankaya.ie552.denizatlihan.drawer.Checkpoint;
import cankaya.ie552.denizatlihan.drawer.IDrawer;
import cankaya.ie552.denizatlihan.drawer.IObstacle;

public class Particle implements IDrawer {

    private int rOut = 8;
    private int rIn = 6;

    private static final double c1 = 0.8d;
    private static final double c2 = 0.3d;

    private static final int len = 10;
    private static final double maxHeadingChange = Math.PI / 6;

    private Color colorOut = new Color(100, 10, 100);
    private Color colorIn = new Color(100, 100, 250);
    private ParticleHistory<CoordinateVector> history = new ParticleHistory<CoordinateVector>();
    private CoordinateVector current;

    public Particle(int centerX, int centerY, double heading) {

        this.current = new CoordinateVector(centerX, centerY, heading);
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

    public CoordinateVector calculateNextLocation(List<IObstacle> obstacles, Checkpoint finish,
            int globalBestDistance) {

        history.push(new CoordinateVector(current.x, current.y, current.heading));

        CoordinateVector candidate = calculateRandomPosition(current, finish);

        int historyLimit = history.size() - 2;

        boolean available = false;

        while (available == false) {

            available = true;

            for (IObstacle obstacle : obstacles) {

                if (obstacle.contains(candidate)) {

                    if (history.size() > historyLimit) {

                        CoordinateVector last = history.pop();
                        current.x = last.x;
                        current.y = last.y;
                        current.heading = last.heading;
                    }

                    available = false;
                    candidate = calculateRandomPosition(current, finish);
                    break;
                }
            }
        }

        current.x = candidate.x;
        current.y = candidate.y;
        current.heading = candidate.heading;

        return null;
    }

    private CoordinateVector calculateRandomPosition(CoordinateVector current, Checkpoint finish) {

        double randomChange = (Math.random() * maxHeadingChange) - (maxHeadingChange / 2);

        CoordinateVector candidate = new CoordinateVector(current.x, current.y, current.heading);

        candidate.x = (int) (current.x + len * c1 * Math.cos(current.heading + randomChange));
        candidate.y = (int) (current.y + len * c1 * Math.sin(current.heading + randomChange));

        double angleBetween = Utility.angleBetween(candidate, finish);

        candidate.x += (int) (len * c2 * Math.cos(angleBetween));
        candidate.y += (int) (len * c2 * Math.sin(angleBetween));

        candidate.heading = Utility.angleBetween(current, candidate);

        return candidate;
    }

    public boolean inside(Checkpoint finish) {

        return finish.contains(current);
    }

    public void drawHistory(Graphics g) {

        g.setColor(Color.cyan);

        for (int i = 0; i < history.size() - 1; i++) {

            CoordinateVector c1 = history.get(i);
            CoordinateVector c2 = history.get(i + 1);

            g.drawLine(c1.x, c1.y, c2.x, c2.y);

            g.setColor(colorOut);
            g.fillOval(c2.x - 3, c2.y - 3, 6, 6);
        }
    }

    public CoordinateVector getCoordinateVector() {

        return current;
    }

}
