package worms.ai;

import worms.Common;
import worms.ai.neuralnet.NeuralNetwork;
import worms.ai.neuralnet.Vector;
import worms.model.Model;
import worms.model.Worm;

import java.awt.geom.Point2D;

/**
 * @author Václav Blažej
 */
public class AiNeuralBrain extends AiBrain {

    private static int RAYS = 5;
    private NeuralNetwork network;

    public AiNeuralBrain(AiNeuralBrain copy) {
        this.network = new NeuralNetwork(copy.getNetwork());
    }

    public AiNeuralBrain() {
        this.network = new NeuralNetwork();
        network.prepare(RAYS, 0, 2);
    }

    @Override
    public void think(Worm worm, Model model) {
        final Point2D.Double position = worm.getPosition();
        double angle = worm.getAngle();
        Vector input = new Vector(RAYS);
        for (int i = 0; i < RAYS; i++) {
            angle += 2 * Math.PI / RAYS;
            double distance = model.getDistance(position, new Point2D.Double(Math.cos(angle), Math.sin(angle)));
            input.set(i, 1 - Common.sigmoid(distance / 200)); // precision for close walls
        }
        final Vector output = network.tick(input);
        final Double left = output.get(0);
        final Double right = output.get(1);

//        System.out.println("VECTOR: " + network.getState());
        worm.setDirection(2 * Common.sigmoid(2 * (left - right)) - 1);
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

}
