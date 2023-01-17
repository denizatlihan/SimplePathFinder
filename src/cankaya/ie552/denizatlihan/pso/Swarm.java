package cankaya.ie552.denizatlihan.pso;

import java.util.ArrayList;
import java.util.List;

import cankaya.ie552.denizatlihan.drawer.Checkpoint;
import cankaya.ie552.denizatlihan.drawer.IObstacle;

public class Swarm {

    private List<Particle> particles = new ArrayList<Particle>();
    private Coordinate globalBest;

    public Swarm(int numberOfParticles, int startX, int startY) {

        for (int i = 0; i < numberOfParticles; i++) {

            particles.add(new Particle(startX, startY));
        }

        globalBest = new Coordinate(startX, startY);
    }

    public List<Particle> getParticles() {

        return particles;
    }

    public Particle iterate(List<IObstacle> obstacles, Checkpoint finish) {

        int globalBestDistance = finish.distanceTo(globalBest);

        for (Particle particle : particles) {

            Coordinate newDistance = particle.calculateNextLocation(obstacles, finish, globalBestDistance);

            if (newDistance != null) {

                globalBest = newDistance;
                globalBestDistance = finish.distanceTo(globalBest);
            }

            if (particle.inside(finish)) {

                return particle;
            }

        }

        return null;
    }
}
