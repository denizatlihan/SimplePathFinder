package cankaya.ie552.denizatlihan.ghostwalker;

import java.awt.Color;
import java.awt.Graphics2D;

public class IndexRect {

    public int x;
    public int cx;
    public int cy;
    public int y;
    public int w;
    public int h;
    public int x2;
    public int y2;
    public int row;
    public int col;
    public boolean walked;
    public boolean terminal;
    public boolean forbidden;
    public boolean deadEnd;
    private Color border = new Color(100, 100, 100, 20);
    private Color forbiddenColor = new Color(200, 50, 50, 70);
    private Color terminalColor = new Color(50, 210, 50, 70);
    private Color walkedColor = new Color(100, 100, 250, 100);
    private Color deadEndColor = new Color(200, 100, 50, 100);

    public IndexRect(int x, int y, int w, int h, int row, int col) {

        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.x2 = x + w;
        this.y2 = y + h;
        this.row = row;
        this.col = col;
        this.cx = (x + w) / 2;
        this.cy = (y + h) / 2;
    }

    public void paint(Graphics2D g2) {

        if (forbidden == true) {

            g2.setColor(forbiddenColor);
            g2.fillRect(x, y, w, h);
        } else if (deadEnd == true) {

            g2.setColor(deadEndColor);
            g2.fillRect(x, y, w, h);
        } else if (terminal == true) {

            g2.setColor(terminalColor);
            g2.fillRect(x, y, w, h);

        } else if (walked == true) {

            g2.setColor(walkedColor);
            g2.fillRect(x, y, w, h);
        }
        g2.setColor(border);
        g2.drawRect(x, y, w, h);
    }

    public boolean contains(int startX, int startY) {

        if (x <= startX && startX <= x2 && y <= startY && startY <= y2) {

            return true;
        }
        return false;
    }

    public int distanceTo(IndexRect bestAvail) {

        return (int) Math.sqrt(Math.pow(cx - bestAvail.cx, 2) + Math.pow(cy - bestAvail.cy, 2));
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof IndexRect) {

            IndexRect rect = (IndexRect) obj;

            return rect.row == row && rect.col == col;
        }
        return super.equals(obj);
    }
}
