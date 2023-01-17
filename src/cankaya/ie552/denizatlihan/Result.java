package cankaya.ie552.denizatlihan;

import java.awt.Graphics;

import cankaya.ie552.denizatlihan.drawer.IDrawer;
import cankaya.ie552.denizatlihan.pso.Particle;

public class Result implements IDrawer {

    private Particle arrivedParticle;
    private long iteration;
    private Long realElapsedTime;

    public Result(Particle arrivedParticle, long iteration, Long realElapsedTime) {

        this.arrivedParticle = arrivedParticle;
        this.iteration = iteration;
        this.realElapsedTime = realElapsedTime;
    }

    @Override
    public void draw(Graphics g) {

        arrivedParticle.drawHistory(g);
    }

}
