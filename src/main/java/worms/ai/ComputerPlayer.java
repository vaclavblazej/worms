package worms.ai;

import worms.model.Model;
import worms.model.Player;

import java.awt.*;

/**
 * @author Václav Blažej
 */
public class ComputerPlayer extends Player {

    private AiBrain brain;

    public ComputerPlayer(int id, String name, Color color, AiBrain brain) {
        super(id, name, color);
        this.brain = brain;
    }

    @Override
    public void prepare(Model model) {
        brain.think(getWorm(), model);
    }

    public AiBrain getBrain() {
        return brain;
    }

    public void setBrain(AiBrain brain) {
        this.brain = brain;
    }
}
