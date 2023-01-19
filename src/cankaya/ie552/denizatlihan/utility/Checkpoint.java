package cankaya.ie552.denizatlihan.utility;

import java.awt.Color;
import java.awt.Graphics;

public class Checkpoint implements IDrawer {

    private int x;
    private int y;
    private int w;
    private int h;
    private Color color = new Color(10, 50, 10);

    public Checkpoint(int x, int y, int w, int h) {

        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    @Override
    public void draw(Graphics g) {

        g.setColor(color);
        g.fillRect(x, y, w, h);
    }

    public int getCenterX() {

        return x + w / 2;
    }

    public int getCenterY() {

        return y + h / 2;
    }

    public boolean contains(int centerX, int centerY) {

        int distanceX = centerX - x;

        if (distanceX < 0) {

            return false;
        }

        if (distanceX > w) {

            return false;
        }

        int distanceY = centerY - y;

        if (distanceY < 0) {

            return false;
        }

        if (distanceY > h) {

            return false;
        }

        return true;

    }

    public boolean contains(CoordinateVector c) {

        return contains(c.x, c.y);
    }

    public int distanceTo(CoordinateVector coordinate) {

        return Utils.distance(x, y, coordinate.x, coordinate.y);
    }
}
