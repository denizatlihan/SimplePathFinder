package cankaya.ie552.denizatlihan;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import cankaya.ie552.denizatlihan.pso.Particle;
import cankaya.ie552.denizatlihan.utility.Checkpoint;
import cankaya.ie552.denizatlihan.utility.IObstacle;

@SuppressWarnings("serial")
public class TestMedia extends JComponent {

    private Checkpoint start;
    private Checkpoint finish;
    private List<IObstacle> obstacles = new ArrayList<IObstacle>();
    private List<Particle> particles = new ArrayList<Particle>();

    @Override
    public void paint(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        start.draw(g);
        finish.draw(g);

        for (IObstacle obstacle : obstacles) {

            obstacle.draw(g);
        }

        for (Particle particle : particles) {

            particle.draw(g);
        }

    }

    public void setStart(Checkpoint checkpoint) {

        start = checkpoint;
    }

    public void setFinish(Checkpoint checkpoint) {

        finish = checkpoint;
    }

    public void setObstacles(List<IObstacle> createdObstacles) {

        this.obstacles = createdObstacles;
    }

    public void setParticles(List<Particle> particles) {

        this.particles = particles;
    }
}
