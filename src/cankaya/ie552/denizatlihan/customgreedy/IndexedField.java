package cankaya.ie552.denizatlihan.customgreedy;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import cankaya.ie552.denizatlihan.TestMedia;
import cankaya.ie552.denizatlihan.utility.Utils;

public class IndexedField {

    private IndexRect[][] rects;
    private IndexRect start;
    private IndexRect finish;

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

    public void solve(TestMedia media, int fps) {

        List<IndexRect> path = new ArrayList<IndexRect>();

        IndexRect current = start;

        while (current != null && finish.equals(current) == false) {

            long t0 = System.currentTimeMillis();

            IndexRect rect = searchNext(current, finish);

            if (rect == null) {

                current.forbidden = true;

                // current = path.get(path.size() - 1);

                path.remove(current);
                current = path.get(path.size() - 1);

                long elapsed = System.currentTimeMillis() - t0;

                Utils.sleep((1000 / fps) - elapsed);
                continue;
            } else {

                current = rect;
                current.walked = true;
            }

            path.add(current);

            long elapsed = System.currentTimeMillis() - t0;

            Utils.sleep((1000 / fps) - elapsed);

            media.repaint();
            System.out.println("completed");
        }

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
}
