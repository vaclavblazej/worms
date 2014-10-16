package worms;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author Pajcak & Venca
 */
public class MyLine {

    private Point.Double a, b;

    public void draw(BufferedImage i, Color color) {
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
