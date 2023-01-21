package cankaya.ie552.denizatlihan.customgreedy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import cankaya.ie552.denizatlihan.TestMedia;
import cankaya.ie552.denizatlihan.utility.Utils;

public class IndexedField {

    private IndexRect[][] rects;
    private IndexRect start;
    private IndexRect finish;
    private int iterations;
    private long totalElapsed;

    public IndexedField(int rows, int cols) {

        rects = new IndexRect[rows + 1][cols + 1];
    }

    public void add(IndexRect r) {

        rects[r.row][r.col] = r;
    }

    public void drawField(Graphics2D g2) {

        for (int i = 0; i < rects.length; i++) {

            for (int j = 0; j < rects[0].length; j++) {

                rects[i][j].paint(g2);
            }
        }
    }

    public void setStartingRect(IndexRect start) {

        this.start = start;
    }

    public void setFinishingRect(IndexRect finish) {

        this.finish = finish;
    }

    public GreedyResult solve(TestMedia media, int fps, long indexingTime) {

        iterations = 0;
        totalElapsed = 0;

        List<IndexRect> path = new ArrayList<IndexRect>();

        IndexRect current = start;

        while (current != null && finish.equals(current) == false) {

            iterations++;

            long t0 = System.nanoTime();

            IndexRect rect = searchNext(current, finish);

            if (rect == null) {

                current.deadEnd = true;

                path.remove(current);
                current = path.get(path.size() - 1);

                long elapsed = System.nanoTime() - t0;

                totalElapsed += elapsed;
                Utils.sleep((1000 / fps) - (elapsed / 1000000));

                continue;
            }

            current = rect;
            current.walked = true;

            path.add(current);

            long elapsed = System.nanoTime() - t0;

            totalElapsed += elapsed;
            Utils.sleep((1000 / fps) - (elapsed / 1000000));

            media.repaint();
        }

        return new GreedyResult(iterations, totalElapsed, indexingTime);

    }

    private IndexRect searchNext(IndexRect current, IndexRect finish) {

        IndexRect next = null;

        int minDistance = finish.distanceTo(current);

        next = nextAvailableNewDistance(current, finish, next, minDistance, -1, 0);
        next = nextAvailableNewDistance(current, finish, next, minDistance, 0, -1);
        next = nextAvailableNewDistance(current, finish, next, minDistance, 0, 1);
        next = nextAvailableNewDistance(current, finish, next, minDistance, 1, 0);

        return next;
    }

    private IndexRect nextAvailableNewDistance(IndexRect current, IndexRect finish, IndexRect next, int minDistance,
            int rowPad, int colPad) {

        IndexRect rect = getAvailCandidate(rects, current.row + rowPad, current.col + colPad);

        if (rect != null) {

            int newDistance = rect.distanceTo(finish);

            if (newDistance < minDistance) {

                minDistance = newDistance;
                next = rect;
            }
        }

        return next;
    }

    private IndexRect getAvailCandidate(IndexRect[][] rects2, int candRow, int candCol) {

        if (candRow > -1 && candRow < rects.length) {

            if (candCol > -1 && candCol < rects[0].length) {

                IndexRect cand = rects[candRow][candCol];

                if (cand.forbidden == false && cand.walked == false) {

                    return cand;
                }
            }
        }

        return null;
    }

    public void drawSummary(Graphics2D g2, long elapsedForIndexing) {

        g2.setColor(Color.white);
        g2.drawString("Iteration: " + iterations, 10, 450);
        g2.drawString("Elapsed(ms): " + (elapsedForIndexing + totalElapsed) / 1000000, 10, 470);

    }
}
