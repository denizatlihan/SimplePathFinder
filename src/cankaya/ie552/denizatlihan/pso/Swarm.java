package cankaya.ie552.denizatlihan.pso;

import java.util.ArrayList;
import java.util.List;

import cankaya.ie552.denizatlihan.utility.Checkpoint;
import cankaya.ie552.denizatlihan.utility.CoordinateVector;
import cankaya.ie552.denizatlihan.utility.IObstacle;

public class Swarm {

    private List<Particle> particles = new ArrayList<Particle>();
    private CoordinateVector globalBest;
    private int bestDistance;

    public Swarm(int numberOfParticles, int startX, int startY) {

        for (int i = 0; i < numberOfParticles; i++) {

            particles.add(new Particle(startX, startY, Math.PI / 6));
        }

        globalBest = particles.get(0).getCoordinateVector();
        bestDistance = Integer.MAX_VALUE;
    }

    public List<Particle> getParticles() {

        return particles;
    }

    public Particle iterate(List<IObstacle> obstacles, Checkpoint finish) {

        for (Particle particle : particles) {

            int distance = particle.calculateNextLocation(obstacles, finish, globalBest);

            if (particle.inside(finish)) {

                return particle;
            }

            if (distance < bestDistance) {

                globalBest = particle.getCoordinateVector();
            }
        }

        return null;
    }

}
