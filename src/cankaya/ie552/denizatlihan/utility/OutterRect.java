package cankaya.ie552.denizatlihan.utility;

import java.awt.Graphics;

public class OutterRect implements IObstacle {

    private int x;
    private int y;
    private int x2;
    private int y2;

    public OutterRect(int x, int y, int x2, int y2) {

        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;

    }

    @Override
    public boolean contains(int centerX, int centerY) {

        if (centerX < x || centerX > x2) {
            return true;
        }

        if (centerY < y || centerY > y2) {
            return true;
        }

        return false;
    }

    @Override
    public void draw(Graphics g) {

        // TODO Auto-generated method stub
    }

    @Override
    public boolean contains(CoordinateVector candidate) {

        return contains(candidate.x, candidate.y);
    }

}
