package cankaya.ie552.denizatlihan;

import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import cankaya.ie552.denizatlihan.drawer.Checkpoint;
import cankaya.ie552.denizatlihan.drawer.CircularObstacle;
import cankaya.ie552.denizatlihan.drawer.IObstacle;
import cankaya.ie552.denizatlihan.pso.PsoSolver;

public class MainTest {

    public static void main(String[] args) {

        // 2D experimental simulation media
        TestMedia media = new TestMedia();

        // Starting point of the path to be calculated
        Checkpoint start = new Checkpoint(0, 0, 50, 50);
        media.setStart(start);

        // Finishing point of the path to be calculated
        Checkpoint finish = new Checkpoint(450, 450, 50, 50);
        media.setFinish(finish);

        // Circular obstacles for test media
        List<IObstacle> obstacles = createObstacles();
        media.setObstacles(obstacles);

        // Showing test media as a frame
        showFrame(media);

        // Solving path planning problem with particle swarm optimization
        PsoSolver psoSolver = new PsoSolver(media, start, finish, obstacles, 20);
        Result resultPso = psoSolver.solve(20000, 50);

        // Showing pso results
        media.showResult(resultPso);

        // // Solving path planning problem with custom greedy method
        // GreedySolver greedySolver = new GreedySolver();
        // Result resultGreedy = greedySolver.solve();
        //
        // // Showing custom results
        // media.showResult(resultGreedy);

    }

    private static List<IObstacle> createObstacles() {

        int r = 130;

        return Arrays.asList(
                new CircularObstacle(100, 200, r),
                new CircularObstacle(200, 100, r),
                new CircularObstacle(250, 250, r),
                new CircularObstacle(300, 400, r),
                new CircularObstacle(400, 300, r),
                new OutterRect(0, 0, 500, 500));
    }

    private static void showFrame(TestMedia media) {

        JFrame frame = new JFrame();
        frame.setSize(516, 540);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(media);
        frame.setVisible(true);
    }

}