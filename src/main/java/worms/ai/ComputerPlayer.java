package worms.ai;

import worms.Common;
import worms.ai.evolution.Individual;
import worms.ai.neuralnet.Matrix;
import worms.ai.neuralnet.NeuralNetwork;
import worms.model.Model;
import worms.model.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Václav Blažej
 */
public class ComputerPlayer extends Player implements Individual {

    private AiBrain brain;

    protected ComputerPlayer() {
        this("", Color.red, null);
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
        return new ComputerPlayer("mutant", this.getColor(), newBrain);
    }

    @Override
    public Individual cross(Individual aa) {
        NeuralNetwork a = ((AiNeuralBrain) ((ComputerPlayer) aa).getBrain()).getNetwork();
        NeuralNetwork b = ((AiNeuralBrain) this.getBrain()).getNetwork();
        Matrix oldMatrix = b.getMatrix();
        NeuralNetwork neuralNetwork = new NeuralNetwork(a);
        List<Boolean> swap = new ArrayList<>();
        Matrix matrix = neuralNetwork.getMatrix();
        if (Common.getRandomBoolean(0.5)) {
            for (int i = 0; i < matrix.height; i++) {
                swap.add(Common.getRandomBoolean(Common.random.nextDouble()));
            }
            for (int i = 0; i < matrix.height; i++) {
                if (swap.get(i)) {
                    for (int j = 0; j < matrix.width; j++) {
                        matrix.getMatrix().get(i).set(j, oldMatrix.getMatrix().get(i).get(j));
                    }
                }
            }
            AiNeuralBrain brain = new AiNeuralBrain();
            brain.setNetwork(neuralNetwork);
        } else {
            for (int i = 0; i < matrix.height; i++) {
                for (int j = 0; j < matrix.width; j++) {
                    if (Common.random.nextInt() % 2 == 0) {
                        matrix.getMatrix().get(i).set(j, oldMatrix.getMatrix().get(i).get(j));
                    }
                }
            }
            AiNeuralBrain brain = new AiNeuralBrain();
            brain.setNetwork(neuralNetwork);
        }
        return new ComputerPlayer("cross", Common.randomColor(), brain);
    }

    @Override
    public double distance(Individual individual) {
        final NeuralNetwork network = ((AiNeuralBrain) (((ComputerPlayer) individual).brain)).getNetwork();
        return ((AiNeuralBrain) brain).getNetwork().getMatrix().distance(network.getMatrix());
    }

    @Override
    public Individual copy() {
        return new ComputerPlayer(getName(), getColor(), new AiNeuralBrain((AiNeuralBrain) getBrain()));
    }
}
