package worms.ai;

import worms.ai.neuralnet.NeuralNetwork;
import worms.ai.neuralnet.Vector;
import worms.model.Model;
import worms.model.Worm;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Václav Blažej
 */
public class AiNeuralBrain extends AiBrain {

    private static int RAYS = 10;
    private NeuralNetwork network;

    public AiNeuralBrain(AiNeuralBrain copy) {
        this.network = new NeuralNetwork(copy.getNetwork());
    }

    public AiNeuralBrain() {
        this.network = new NeuralNetwork();
        network.prepare(RAYS, 1, 1);
    }

    @Override
    public void think(Worm worm, Model model) {
        final Point2D.Double position = worm.getPosition();
        double angle = worm.getAngle();
        Vector input = new Vector(RAYS);
        model.clearLines();
        for (int i = 0; i < RAYS; i++) {
            angle += 2 * Math.PI / RAYS;
            double distance = model.getDistance(position, new Point2D.Double(Math.cos(angle), Math.sin(angle)));
            input.set(i, sigmoid(distance / 500)); // precision for close walls
        }
        final Vector output = network.tick(input);
        final Double result = output.get(0);

//        System.out.println("VECTOR: " + network.getState());
        worm.setDirection(result);
    }

    @Override
    public void reset() {
        network.resetState();
    }

    public NeuralNetwork getNetwork() {
        return network;
    }

    public void setNetwork(NeuralNetwork network) {
        this.network = network;
    }

    private double sigmoid(double value) {
        return 2 / (1 + Math.exp(-value)) - 1;
    }
}
