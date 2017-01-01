package worms.model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * @author Václav Blažej
 */
public final class Scenario {

    public Point.Double origin;
    public BufferedImage image;
    public double degree;

    public Scenario(Point.Double origin, BufferedImage image, double degree) {
        this.origin = origin;
        this.image = image;
        this.degree = degree;
    }
}
