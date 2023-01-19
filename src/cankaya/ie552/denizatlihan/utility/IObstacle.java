package cankaya.ie552.denizatlihan.utility;

public interface IObstacle extends IDrawer {

    boolean contains(int centerX, int centerY);

    boolean contains(CoordinateVector candidate);
}
