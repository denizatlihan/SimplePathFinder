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
    private List<IndexRect> path;
    private GreedyResult result;

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

        path = new ArrayList<IndexRect>();

        IndexRect current = start;

        while (current != null && finish.equals(current) == false) {

            iterations++;

            long t0 = System.nanoTime();

            IndexRect rect = searchNext(current, finish);

            if (rect == null) {

                current.deadEnd = true;

                path.remove(current);

                if (path.size() == 0) {

                    result = new GreedyResult(iterations, totalElapsed, indexingTime, path);
                    return result;
                }
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

        result = new GreedyResult(iterations, totalElapsed, indexingTime, path);
        return result;

    }

    private IndexRect searchNext(IndexRect current, IndexRect finish) {

        IndexRect next = null;

        int minDistance = finish.distanceTo(current);

        next = nextAvailableNewDistance(current, finish, next, minDistance, -1, 0);

        // if (next == null) {
        //
        next = nextAvailableNewDistance(current, finish, next, minDistance, 0, -1);
        // }
        //
        // if (next == null) {
        //
        next = nextAvailableNewDistance(current, finish, next, minDistance, 0, 1);
        // }
        //
        // if (next == null) {
        //
        next = nextAvailableNewDistance(current, finish, next, minDistance, 1, 0);
        // }

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

    public void drawPath(Graphics2D g2) {

        double totalLength = 0;

        ArrayList<IndexRect> calcPath = new ArrayList<>(path);
        calcPath.add(0, start);

        for (int i = 1; i < calcPath.size() - 1; i++) {

            IndexRect next = calcPath.get(i + 1);
            IndexRect current = calcPath.get(i);
            IndexRect prev = calcPath.get(i - 1);

            g2.setColor(Color.cyan);
            if (prev.row == current.row && current.row == next.row) {

                g2.drawLine(prev.x + prev.w / 2, prev.y + prev.h, current.x + current.w / 2, current.y + current.h);
                totalLength += prev.w;

            } else if (prev.col == current.col && current.col == next.col) {

                g2.drawLine(prev.x + prev.w, prev.y + prev.h / 2, current.x + current.w, current.y + current.h / 2);
                totalLength += prev.h;

            } else if (prev.row == current.row && current.row < next.row) {

                g2.setColor(Color.magenta);
                g2.drawArc(prev.x + prev.w / 2, prev.y + prev.h / 2, current.w, current.h, 180, 90);
                totalLength += Math.PI * prev.w / 4;

            } else if (prev.row == current.row && current.row > next.row) {

                g2.setColor(Color.green);
                g2.drawArc(prev.x + prev.w, prev.y, current.w, current.h, 90, 90);
                totalLength += Math.PI * prev.w / 4;
            } else if (prev.col == current.col && current.col < next.col) {

                g2.setColor(Color.yellow);
                g2.drawArc(prev.x + prev.w / 2, prev.y + prev.h / 2, current.w, current.h, 0, 90);
                totalLength += Math.PI * prev.w / 4;
            } else if (prev.col == current.col && current.col > next.col) {

                g2.setColor(Color.white);
                g2.drawArc(prev.x, prev.y, current.w, current.h, 270, 90);
                totalLength += Math.PI * prev.w / 4;
            }
        }

        g2.drawString(String.format("Path Length: %.2f", totalLength), 10, 490);
    }
}
