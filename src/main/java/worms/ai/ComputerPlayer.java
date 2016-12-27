package worms.ai;

import worms.Common;
import worms.ai.evolution.Individual;
import worms.ai.neuralnet.Matrix;
import worms.ai.neuralnet.NeuralNetwork;
import worms.model.Model;
import worms.model.Player;

import java.awt.*;
import java.util.Random;

/**
 * @author Václav Blažej
 */
public class ComputerPlayer extends Player implements Individual {

    private AiBrain brain;

    protected ComputerPlayer() {
    }

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
        AiNeuralBrain newBrain = new AiNeuralBrain(brain);
        newBrain.setNetwork(newBrain.getNetwork().mutate());
        return new ComputerPlayer(this.getName(), this.getColor(), newBrain);
    }

    @Override
    public Individual cross(Individual aa) {
        NeuralNetwork a = ((AiNeuralBrain) ((ComputerPlayer) aa).getBrain()).getNetwork();
        NeuralNetwork b = ((AiNeuralBrain) this.getBrain()).getNetwork();
        Matrix oldMatrix = b.getMatrix();
        NeuralNetwork neuralNetwork = new NeuralNetwork(a);
        Matrix matrix = neuralNetwork.getMatrix();
        Random random = new Random();
        for (int i = 0; i < matrix.height; i++) {
            for (int j = 0; j < matrix.width; j++) {
                if (random.nextInt() % 2 == 0) {
                    matrix.getMatrix().get(i).set(j, oldMatrix.getMatrix().get(i).get(j));
                }
            }
        }
        AiNeuralBrain brain = new AiNeuralBrain();
        brain.setNetwork(neuralNetwork);
        return new ComputerPlayer(this.getName(), Common.randomColor(), brain);
    }
}
