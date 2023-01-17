package cankaya.ie552.denizatlihan;

public class Utility {

    public static int distance(int x, int y, int x2, int y2) {

        return (int) Math.sqrt(Math.pow(x - x2, 2) + Math.pow(y - y2, 2));
    }

}
