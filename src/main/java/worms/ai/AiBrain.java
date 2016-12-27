package worms.ai;

import worms.model.Model;
import worms.model.Worm;

import java.io.Serializable;
import java.util.Random;

/**
 * @author Václav Blažej
 */
public abstract class AiBrain implements Serializable{

    protected Random random;

    public AiBrain() {
        this.random = new Random();
    }

    public abstract void think(Worm worm, Model model);

    public abstract void reset();
}
