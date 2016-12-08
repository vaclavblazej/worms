package worms.model;

/**
 * @author Patrik Faistaver
 * @author Václav Blažej
 * @author Štěpán Plachý
 */
public class Direction {
    public static final double LEFT = 0;
    public static final double STRAIGHT = 0.5;
    public static final double RIGHT = 1;

    private double direction; // 0 left, 0.5 straight, 1 right

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public double getRotation(double rotationSpeed) {
        return rotationSpeed * (direction - STRAIGHT);
    }
}
