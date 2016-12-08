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
    public List<Point.Double> others;
    private NeuralNetwork network;

    public AiNeuralBrain() {
        this.others = new ArrayList<>();
        this.network = new NeuralNetwork();
        network.prepare(RAYS, 1, 1);
    }

    @Override
    public void think(Worm worm, Model model) {
        others.clear();
        final Point2D.Double position = worm.getPosition();
        double angle = worm.getAngle();
        Vector input = new Vector(RAYS);
        model.clearLines();
        for (int i = 0; i < RAYS; i++) {
            angle += 2 * Math.PI / RAYS;
            double distance = model.getDistance(position, new Point2D.Double(Math.cos(angle), Math.sin(angle)));
            input.setValue(i, Math.min(1, distance / 800));
        }
        final Vector output = network.tick(input);
        final Double result = sigmoid(output.get(0));

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
        return 1 / (1 + Math.exp(-value));
    }
}
