package cankaya.ie552.denizatlihan.drawer;

import cankaya.ie552.denizatlihan.pso.CoordinateVector;

public interface IObstacle extends IDrawer {

    boolean contains(int centerX, int centerY);

    boolean contains(CoordinateVector candidate);
}
