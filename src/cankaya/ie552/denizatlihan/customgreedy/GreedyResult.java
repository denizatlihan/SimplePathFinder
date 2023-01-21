package cankaya.ie552.denizatlihan.customgreedy;

public class GreedyResult {

    private int iterations;
    private long totalElapsed;
    private long indexingTime;

    public GreedyResult(int iterations, long totalElapsed, long indexingTime) {

        this.iterations = iterations;
        this.totalElapsed = totalElapsed;
        this.indexingTime = indexingTime;
    }

    public void print() {

        System.out.println(
                "Custom Greedy Result -> Iteration: " + iterations + ", Elapsed Time: "
                        + ((totalElapsed + indexingTime) / 1000000));
    }

}
