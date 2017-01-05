package worms.model;

import java.awt.*;

/**
 * @author Václav Blažej
 */
public final class Scenario {

    public Point.Double origin;
    public CachedBitmap image;
    public double degree;

    public Scenario(Point.Double origin, CachedBitmap image, double degree) {
        this.origin = origin;
        this.image = image;
        this.degree = degree;
    }
}
