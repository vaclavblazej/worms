package worms.model;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * @author Patrik Faistaver
 * @author Václav Blažej
 * @author Štěpán Plachý
 */
public final class Worm {

    private Point.Double position;
    private double angle;
    private Direction direction;
    private int phaseShiftTimer;
    private boolean phaseShift;

    public Worm(int x, int y, int angle) {
        position = new Point.Double(x, y);
        this.angle = angle;
        direction = new Direction();
        direction.setDirection(Direction.STRAIGHT);
        phaseShiftTimer = 5;
        phaseShift = false;
    }

    public boolean isPhaseShifted() {
        return phaseShift;
    }

    public void setPhaseShift(boolean phaseShift) {
        this.phaseShift = phaseShift;
    }

    public void decreasePhaseShiftTimer(int value) {
        phaseShiftTimer -= value;
    }

    public int getPhaseShiftTimer() {
        return phaseShiftTimer;
    }

    public void setPhaseShiftTimer(int phaseShiftTimer) {
        this.phaseShiftTimer = phaseShiftTimer;
    }

    public Point2D.Double getPosition() {
        return position;
    }

    public void setPosition(Point2D.Double position) {
        this.position = position;
    }

    public void setPosition(double x, double y) {
        setPosition(new Point.Double(x, y));
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction.setDirection(direction);
    }
}
