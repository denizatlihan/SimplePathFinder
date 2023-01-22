package cankaya.ie552.denizatlihan.pso;

import java.util.ArrayList;
import java.util.List;

import cankaya.ie552.denizatlihan.utility.Checkpoint;
import cankaya.ie552.denizatlihan.utility.IObstacle;

public class Swarm {

    private List<Particle> particles = new ArrayList<Particle>();
    private Particle globalBest;
    private int bestDistance;
    private IterationResult iterationResult = new IterationResult();

    public Swarm(int numberOfParticles, int startX, int startY) {

        for (int i = 0; i < numberOfParticles; i++) {

            particles.add(new Particle(startX, startY, Math.PI / 6));
        }

        globalBest = particles.get(0);
        bestDistance = Integer.MAX_VALUE;
    }

    public List<Particle> getParticles() {

        return particles;
    }

    public IterationResult iterate(List<IObstacle> obstacles, Checkpoint finish) {

        for (Particle particle : particles) {

            int distance = particle.calculateNextLocation(obstacles, finish, globalBest.getCoordinateVector());

            if (particle.inside(finish)) {

                iterationResult.found = true;
                iterationResult.particle = particle;
                return iterationResult;
            }

            if (distance < bestDistance) {

                globalBest = particle;
            }

            iterationResult.particle = globalBest;
        }

        return iterationResult;
    }

}
