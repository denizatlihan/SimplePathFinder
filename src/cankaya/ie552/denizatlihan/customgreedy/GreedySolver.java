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
    private int parseWh;

    public GreedySolver(TestMedia media, Checkpoint start, Checkpoint finish, List<IObstacle> obstacles) {

        this.media = media;
        this.start = start;
        this.finish = finish;
        this.obstacles = obstacles;

        int maxLen = 10;
        double maxTurn = Math.PI / 6;
        parseWh = (int) Math.round(maxLen * Math.cos(maxTurn) + maxLen * Math.sin(maxTurn));
    }

    public GreedyResult solve(int fps) {

        long t0 = System.nanoTime();

        IndexedField indexedField = indexMedia(media, obstacles, start, finish);

        long elapsed = System.nanoTime() - t0;

        System.out.println("Indexing completed in " + (elapsed / 1000000) + " ms");

        media.setDrawer(g2 -> {

            indexedField.drawField(g2);
        });

        indexedField.solve(media, fps);

        return null;
    }

    private IndexedField indexMedia(TestMedia media, List<IObstacle> obstacles, Checkpoint start, Checkpoint finish) {

        int rows = media.getHeight() / parseWh;
        int cols = media.getWidth() / parseWh;

        int startX = start.getCenterX();
        int startY = start.getCenterY();

        int finishX = finish.getCenterX();
        int finishY = finish.getCenterY();

        IndexedField indexedField = new IndexedField(rows, cols);

        for (int i = 0; i <= rows; i++) {

            for (int j = 0; j <= cols; j++) {

                IndexRect r = new IndexRect(i * parseWh, j * parseWh, parseWh, parseWh, i, j);

                if (r.contains(startX, startY)) {

                    indexedField.setStartingRect(r);
                    r.terminal = true;
                } else if (r.contains(finishX, finishY)) {

                    indexedField.setFinishingRect(r);
                    r.terminal = true;

                } else {

                    for (IObstacle o : obstacles) {

                        if (o.contains(r.x, r.y)) {

                            r.forbidden = true;
                            break;
                        } else if (o.contains(r.x, r.y2)) {

                            r.forbidden = true;
                            break;
                        } else if (o.contains(r.x2, r.y)) {

                            r.forbidden = true;
                            break;
                        } else if (o.contains(r.x2, r.y2)) {

                            r.forbidden = true;
                            break;
                        }

                    }
                }

                indexedField.add(r);
            }
        }

        return indexedField;
    }
}
