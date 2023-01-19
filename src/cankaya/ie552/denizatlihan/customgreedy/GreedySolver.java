package cankaya.ie552.denizatlihan.customgreedy;

import java.util.List;

import cankaya.ie552.denizatlihan.TestMedia;
import cankaya.ie552.denizatlihan.utility.Checkpoint;
import cankaya.ie552.denizatlihan.utility.IObstacle;

public class GreedySolver {

    private TestMedia media;
    private Checkpoint start;
    private Checkpoint finish;
    private List<IObstacle> obstacles;

    public GreedySolver(TestMedia media, Checkpoint start, Checkpoint finish, List<IObstacle> obstacles) {

        this.media = media;
        this.start = start;
        this.finish = finish;
        this.obstacles = obstacles;
    }

}
