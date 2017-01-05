package worms;

import worms.model.CachedBitmap;

import java.awt.*;

/**
 *
 * @author Patrik Faistaver
 * @author Václav Blažej
 * @author Štěpán Plachý
 */
public class MyLine {

    private Point.Double a, b;

    public void draw(CachedBitmap i, Color color) {
        Graphics2D g = i.createGraphics();
        g.setColor(color);
        g.setStroke(new BasicStroke(2.2f));
        g.drawLine((int) a.x, (int) a.y, (int) b.x, (int) b.y);
    }

    public MyLine(Point.Double a, Point.Double b) {
        this.a = a;
        this.b = b;
    }
}
