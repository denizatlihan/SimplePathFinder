package cankaya.ie552.denizatlihan.ghostwalker;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

public class WalkerResult {

    public int iterations;
    public long totalElapsed;
    public long indexingTime;
    public List<IndexRect> path;
    private IndexedField indexedField;

    public WalkerResult(int iterations, long totalElapsed, long indexingTime, List<IndexRect> path) {

        this.iterations = iterations;
        this.totalElapsed = totalElapsed;
        this.indexingTime = indexingTime;
        this.path = path;
    }

    public void print() {

        System.out.println(
                "Ghost Walker-> Iteration: " + iterations + ", Elapsed Time(ms): "
                        + ((totalElapsed + indexingTime) / 1000000));
    }

    public void draw(Graphics2D g2) {

        if (path.size() == 0) {

            g2.setColor(Color.red);
            g2.drawString("Solution Not Found!", 10, 430);
        }

        if (indexedField != null) {

            indexedField.drawField(g2);
            indexedField.drawSummary(g2, totalElapsed + indexingTime);
            indexedField.drawPath(g2);
        }

    }

    public void setIndexedField(IndexedField indexedField) {

        this.indexedField = indexedField;
    }

}
