package cankaya.ie552.denizatlihan;

import cankaya.ie552.denizatlihan.drawer.Checkpoint;
import cankaya.ie552.denizatlihan.pso.CoordinateVector;

public class Utility {

    public static int distance(int x, int y, int x2, int y2) {

        return (int) Math.sqrt(Math.pow(x - x2, 2) + Math.pow(y - y2, 2));
    }

    public static double angleBetween(CoordinateVector current, Checkpoint finish) {

        return Math.atan(((double) finish.getCenterY() - current.y) / ((double) finish.getCenterX() - current.x));
    }

    public static double angleBetween(CoordinateVector current, CoordinateVector candidate) {

        return Math.atan(((double) candidate.y - current.y) / ((double) candidate.x - current.x));
    }

}
