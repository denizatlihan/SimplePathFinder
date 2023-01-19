package cankaya.ie552.denizatlihan.drawer;

import java.awt.Color;
import java.awt.Graphics;

import cankaya.ie552.denizatlihan.Utility;
import cankaya.ie552.denizatlihan.pso.CoordinateVector;

public class CircularObstacle implements IObstacle {

    private int x;
    private int y;
    private int r;
    private Color color = new Color(150, 10, 10);
    private int xUpperLeft;
    private int yUpperLeft;

    public CircularObstacle(int x, int y, int r) {

        this.xUpperLeft = x - r / 2;
        this.yUpperLeft = y - r / 2;
        this.x = x;
        this.y = y;
        this.r = r;
    }

    @Override
    public void draw(Graphics g) {

        g.setColor(color);
        g.fillOval(xUpperLeft, yUpperLeft, r, r);
    }

    @Override
    public boolean contains(int centerX, int centerY) {

        return Utility.distance(x, y, centerX, centerY) < r / 2;
    }

    @Override
    public boolean contains(CoordinateVector candidate) {

        return contains(candidate.x, candidate.y);
    }

}
