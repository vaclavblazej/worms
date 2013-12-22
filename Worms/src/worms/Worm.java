package worms;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author Pajcak & Venca
 */
public final class Worm {
    //mods

    private boolean throughWalls = false;
    //--------------------
    private static final int SIZE = 6;
    private static final int HALF = SIZE / 2;
    private static final int WARNING = 40;
    private static final int INTERVAL = 180;
    private static final int FAST = 10;
    private static final int SLOW = 1;
    private static final int STEP = 1;
    private static final int ROTATION = 2 * STEP;
    public static final int LENGTH = 2;
    private int counter;
    private Point.Double position;
    private int originalAngle;
    private int angle;
    private boolean crash;
    private boolean left;
    private boolean right;
    private Color color;
    private int change;
    private Score score;

    public Worm(int x, int y, int angle, Color color) {
        this.color = color;
        this.originalAngle = angle;
        counter = new Random().nextInt(INTERVAL); // random time interval to generate first worm division
        change = SLOW;
        score = new Score(0, color);
        left = false;
        right = false;
        reset(x, y);
    }

    public void reset(int x, int y) {
        position = new Point.Double(x, y);
        this.angle = originalAngle;
        crash = false;
    }

    public void tick(BufferedImage i) {
        Point.Double tmp = new Point.Double();
        tmp.x = position.x;
        tmp.y = position.y;
        position.x += STEP * Math.cos(Math.toRadians(angle));
        position.y -= STEP * Math.sin(Math.toRadians(angle));

        angle += (left == right) ? 0 : (left == true) ? ROTATION : -ROTATION;
        if (angle > 360) {
            angle -= 360;
        } else if (angle < 0) {
            angle += 360;
        }

        counter += change; // counting time to generate next worm division
        if (counter > INTERVAL) {
            counter = 0;
            if (change == SLOW) {
                change = FAST;
            } else {
                change = SLOW;
            }
        }
        if (change == SLOW) {
            int x = (int) (position.x + 2 * Math.cos(Math.toRadians(angle)));
            int y = (int) (position.y - 2 * Math.sin(Math.toRadians(angle)));

            if (throughWalls) {
                if (!(x < 800 && x > 0)) {
                    if (tmp.x < 10) {
                        position.x = 798;
                        tmp.x = 799;
                    } else {
                        position.x = 2;
                        tmp.x = 1;
                    }
                } else if (!(y < 600 && y > 0)) {
                    if (tmp.y < 10) {
                        position.y = 598;
                        tmp.y = 599;
                    } else {
                        position.y = 2;
                        tmp.y = 1;
                    }
                } else if (i.getRGB(x, y) != Color.BLACK.getRGB()) {
                    crash = true;
                }
            } else {
                if (x > 800 || x <= 0
                        || y > 600 || y <= 0
                        || i.getRGB(x, y) != Color.BLACK.getRGB()) {
                    crash = true;
                }
            }

            new MyLine(tmp, position).draw(i, color);
        }


    }

    void setLeft(boolean val) {
        left = val;
    }

    void setRight(boolean val) {
        right = val;
    }

    public void draw(Graphics2D g) {
        if (counter + WARNING > INTERVAL) {
            g.setColor(Color.WHITE);
        } else {
            g.setColor(color);
        }
        g.fillOval((int) position.x - HALF, (int) position.y - HALF, SIZE, SIZE);
    }

    public void increaseScore() {
        
        score.points++;
    }

    public Score getScore() {
        return score;
    }

    public boolean isCrash() {
        return crash;
    }
}
