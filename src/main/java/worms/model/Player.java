package worms.model;

import java.awt.*;

/**
 * @author Patrik Faistaver
 * @author Václav Blažej
 * @author Štěpán Plachý
 */
public abstract class Player {

    protected Player(){
    }

    public static int newid = 0;

    private int id;
    private String name;
    private int score;
    private Worm worm;
    private boolean lost;
    private Color color;

    public Player(String name, Color color) {
        this.id = newid++;
        this.name = name;
        this.color = color;
        this.score = 0;
        this.worm = new Worm(0, 0, 0);
        this.lost = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public abstract void prepare(Model model);

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
