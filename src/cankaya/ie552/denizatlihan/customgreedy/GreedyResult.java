package cankaya.ie552.denizatlihan.customgreedy;

import java.util.List;

public class GreedyResult {

    private int iterations;
    private long totalElapsed;
    private long indexingTime;
    private List<IndexRect> path;

    public GreedyResult(int iterations, long totalElapsed, long indexingTime, List<IndexRect> path) {

        this.iterations = iterations;
        this.totalElapsed = totalElapsed;
        this.indexingTime = indexingTime;
        this.path = path;
    }

    public void print() {

        System.out.println(
                "Custom Greedy Result -> Iteration: " + iterations + ", Elapsed Time(ms): "
                        + ((totalElapsed + indexingTime) / 1000000));
    }

}
