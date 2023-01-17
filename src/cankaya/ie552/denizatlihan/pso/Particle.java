package cankaya.ie552.denizatlihan.pso;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import cankaya.ie552.denizatlihan.drawer.Checkpoint;
import cankaya.ie552.denizatlihan.drawer.IDrawer;
import cankaya.ie552.denizatlihan.drawer.IObstacle;

public class Particle implements IDrawer {

    private int centerX;
    private int centerY;
    private int rOut = 8;
    private int rIn = 6;
    private Color colorOut = new Color(100, 10, 100);
    private Color colorIn = new Color(100, 100, 250);
    private ParticleHistory history = new ParticleHistory();

    public Particle(int centerX, int centerY) {

        this.centerX = centerX;
        this.centerY = centerY;
    }

    @Override
    public void draw(Graphics g) {

        g.setColor(colorOut);
        g.fillOval(centerX - rOut / 2, centerY - rOut / 2, rOut, rOut);
        g.setColor(colorIn);
        g.fillOval(centerX - rIn / 2, centerY - rIn / 2, rIn, rIn);
    }

    public Coordinate calculateNextLocation(List<IObstacle> obstacles, Checkpoint finish, int globalBestDistance) {

        history.push(new Coordinate(centerX, centerY));

        int stepX = (int) (Math.random() * 5);
        int stepY = (int) (Math.random() * 5);

        boolean calculate = true;
        boolean pop = false;

        while (calculate) {

            calculate = false;

            for (IObstacle obstacle : obstacles) {

                if (obstacle.contains(centerX + stepX, centerY + stepY)) {

                    if (history.size() >= 0) {
                        Coordinate last = history.pop();
                        centerX = last.x;
                        centerY = last.y;
                    }

                    stepX = (int) (Math.random() * 30);
                    stepY = (int) (Math.random() * 30);
                    calculate = true;
                    break;
                }
            }
        }

        centerX += stepX;
        centerY += stepY;

        return null;
    }

    public boolean inside(Checkpoint finish) {

        return finish.contains(centerX, centerY);
    }

    public void drawHistory(Graphics g) {

        g.setColor(Color.cyan);

        for (int i = 0; i < history.size() - 1; i++) {

            Coordinate c1 = history.get(i);
            Coordinate c2 = history.get(i + 1);

            g.drawLine(c1.x, c1.y, c2.x, c2.y);
        }
    }

}
