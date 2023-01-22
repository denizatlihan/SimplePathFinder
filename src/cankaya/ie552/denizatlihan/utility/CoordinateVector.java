package cankaya.ie552.denizatlihan.utility;

public class CoordinateVector {

    public int x;
    public int y;
    public double heading;

    public CoordinateVector(int x, int y, double heading) {

        this.x = x;
        this.y = y;
        this.heading = heading;
    }

    public CoordinateVector(CoordinateVector candidate) {

        this.x = candidate.x;
        this.y = candidate.y;
        this.heading = candidate.heading;
    }

    public double angleBetween(CoordinateVector c) {

        if (c.x == x) {
            return 0;
        }

        if (c.y == y) {

            return 0;
        }

        return Math.atan((y - c.y) / (x - c.x));
    }

    public double distanceBetween(CoordinateVector c1) {

        return Math.sqrt(Math.pow(c1.x - x, 2) + Math.pow(c1.y - y, 2));
    }

}
