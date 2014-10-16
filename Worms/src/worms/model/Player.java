package worms.model;

import java.awt.Color;

/**
 *
 * @author Venca & Pajcak & Štěpán
 */
public class Player {
    private String name;
    private int score;
    private Worm worm;
    private boolean lost;
    private Color color;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
        this.score = 0;
        this.worm = new Worm(0, 0, 0);
        this.lost = false;
    }

    public void incrementScore() {
        incrementScoreBy(1);
    }

    public void incrementScoreBy(int value) {
        score += value;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Worm getWorm() {
        return worm;
    }

    public void setWorm(Worm worm) {
        this.worm = worm;
    }

    public boolean hasLost() {
        return lost;
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }
}
