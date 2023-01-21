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

        if (arrivedParticle != null) {

            System.out.println(
                    "PSO Result -> Iteration: " + iteration + ", Elapsed Time: " + (realElapsedTime / 1000000));
        } else {

            System.out.println(
                    "PSO Result -> SOLUTION NOT FOUND!  Iteration: " + iteration + ", Elapsed Time: "
                            + (realElapsedTime / 1000000));
        }
    }

}
