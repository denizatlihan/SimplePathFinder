package cankaya.ie552.denizatlihan.utility;

public class Utils {

    public static int distance(int x, int y, int x2, int y2) {

        return (int) Math.sqrt(Math.pow(x - x2, 2) + Math.pow(y - y2, 2));
    }

    public static double angleBetween(CoordinateVector current, Checkpoint finish) {

        return Math.atan(((double) finish.getCenterY() - current.y) / ((double) finish.getCenterX() - current.x));
    }

    public static double angleBetween(CoordinateVector current, CoordinateVector candidate) {

        return Math.atan(((double) candidate.y - current.y) / ((double) candidate.x - current.x));
    }

    public static void sleep(long time) {

        try {
            if (time > 0) {
                Thread.sleep(time);
            }
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }

}
