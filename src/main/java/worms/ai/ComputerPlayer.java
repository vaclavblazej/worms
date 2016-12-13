package worms.ai;

import worms.ai.evolution.Individual;
import worms.model.Model;
import worms.model.Player;

import java.awt.*;

/**
 * @author Václav Blažej
 */
public class ComputerPlayer extends Player implements Individual {

    private AiBrain brain;

    public ComputerPlayer(String name, Color color, AiBrain brain) {
        super(name, color);
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

    @Override
    public int fitness() {
        return getScore();
    }

    @Override
    public Individual mutate() {
        AiNeuralBrain brain = (AiNeuralBrain) this.getBrain();
        return new ComputerPlayer(this.getName() + "m", this.getColor(), new AiNeuralBrain(brain));
    }

    @Override
    public Individual cross(Individual a) {
        System.out.println("todo implement cross operation!");
        return this.mutate();
    }
}
