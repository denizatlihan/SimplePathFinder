package cankaya.ie552.denizatlihan;

import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import cankaya.ie552.denizatlihan.ghostwalker.GhostWalker;
import cankaya.ie552.denizatlihan.ghostwalker.WalkerResult;
import cankaya.ie552.denizatlihan.pso.PsoResult;
import cankaya.ie552.denizatlihan.pso.PsoSolver;
import cankaya.ie552.denizatlihan.utility.Checkpoint;
import cankaya.ie552.denizatlihan.utility.CircularObstacle;
import cankaya.ie552.denizatlihan.utility.IObstacle;
import cankaya.ie552.denizatlihan.utility.OutterRect;

public class MainTest {

    public static void main(String[] args) {

        // 2D experimental simulation media
        TestMedia mediaPso = new TestMedia();
        TestMedia mediaCustom = new TestMedia();

        // Starting point of the path to be calculated
        Checkpoint start = new Checkpoint(0, 0, 10, 10);
        mediaPso.setStart(start);
        mediaCustom.setStart(start);

        // Finishing point of the path to be calculated
        Checkpoint finish = new Checkpoint(490, 490, 10, 10);
        mediaPso.setFinish(finish);
        mediaCustom.setFinish(finish);

        // Circular obstacles for test media
        List<IObstacle> obstacles = createObstacles();
        mediaPso.setObstacles(obstacles);
        mediaCustom.setObstacles(obstacles);

        // Showing test media as a frame
        showFrame(mediaPso, 0, 0, "Modified PSO");
        showFrame(mediaCustom, 530, 0, "Ghost Walker");

        new Thread(() -> solvePSO(mediaPso, start, finish, obstacles, 20)).start();
        new Thread(() -> solveGhostWalker(mediaCustom, start, finish, obstacles, 20)).start();

    }

    private static void solvePSO(TestMedia media, Checkpoint start, Checkpoint finish, List<IObstacle> obstacles,
            int fps) {

        // Solving path planning problem with particle swarm optimization
        PsoSolver psoSolver = new PsoSolver(media, start, finish, obstacles, 20);
        PsoResult psoSolution = psoSolver.solve(300, fps);
        psoSolution.print();

        media.setDrawer(g2 -> {

            psoSolution.draw(g2);
        });
        media.repaint();
    }

    private static void solveGhostWalker(TestMedia media, Checkpoint start, Checkpoint finish,
            List<IObstacle> obstacles, int fps) {

        GhostWalker ghostWalker = new GhostWalker(media, start, finish, obstacles);
        WalkerResult walkerResult = ghostWalker.solve(fps);
        walkerResult.print();
        media.setDrawer(g2 -> {

            walkerResult.draw(g2);
        });
        media.repaint();

    }

    private static List<IObstacle> createObstacles() {

        int r = 100;
        return Arrays.asList(
                new CircularObstacle(150, 50, r),
                new CircularObstacle(100, 100, r),
                new CircularObstacle(350, 150, r),
                new CircularObstacle(200, 100, r),
                new CircularObstacle(100, 200, r),
                new CircularObstacle(200, 100, r),
                new CircularObstacle(250, 250, r),
                new CircularObstacle(300, 400, r),
                new CircularObstacle(400, 300, r),
                new OutterRect(0, 0, 510, 510));
    }

    private static void showFrame(TestMedia media, int x, int y, String frameName) {

        JFrame frame = new JFrame(frameName);
        frame.setSize(520, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(media);
        frame.setVisible(true);
        frame.setLocation(x, y);
    }

}
