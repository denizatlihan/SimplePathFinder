package cankaya.ie552.denizatlihan.pso;

public class PsoResult {

    public Particle arrivedParticle;
    public Long realElapsedTime;
    public boolean solutionFound;
    public long iteration;

    public PsoResult(Particle arrivedParticle, Long realElapsedTime, boolean solutionFound, long iteration) {

        this.arrivedParticle = arrivedParticle;
        this.realElapsedTime = realElapsedTime;
        this.solutionFound = solutionFound;
        this.iteration = iteration;
    }

    public void print() {

        System.out.println("PSO SOLUTION:");
        System.out.println("Iteration: " + iteration);
        System.out.println("# Milestones: " + arrivedParticle.getHistory().size());
        System.out.println("Elapsed Solution Time Without Animation(ms): " + realElapsedTime / 1000000);
    }

}
