package worms.model;

import java.io.Serializable;

/**
 * @author Patrik Faistaver
 * @author Václav Blažej
 * @author Štěpán Plachý
 */
public class Direction implements Serializable{
    public static final double LEFT = 0;
    public static final double STRAIGHT = 0.5;
    public static final double RIGHT = 1;

    private double direction; // 1 left, 0 straight, -1 right

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        if (direction < -1 || direction > 1) throw new RuntimeException("Invalid direction " + direction);
        this.direction = direction;
    }

    public double getRotation(double rotationSpeed) {
        return rotationSpeed * (direction - STRAIGHT);
    }
}
