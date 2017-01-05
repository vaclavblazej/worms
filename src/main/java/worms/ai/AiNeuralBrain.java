package worms.ai;

import worms.Common;
import worms.ai.neuralnet.NeuralNetwork;
import worms.ai.neuralnet.Vector;
import worms.model.Model;
import worms.model.Worm;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * @author Václav Blažej
 */
public class AiNeuralBrain extends AiBrain {

    private static int RAYS = 9;
    private final List<Point2D> points;
    private NeuralNetwork network;

    public AiNeuralBrain(AiNeuralBrain copy) {
        this.network = new NeuralNetwork(copy.getNetwork());
        this.points = Collections.synchronizedList(new ArrayList<>());
    }

    public AiNeuralBrain() {
        this.network = new NeuralNetwork(RAYS, 0, 2);
        this.points = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public void think(Worm worm, Model model) {
        points.clear();
        final Point2D.Double position = worm.getPosition();
        double angle = worm.getAngle();
        Vector input = new Vector(RAYS);
        Function<Double, Double> supplier = (ang) -> {
            Point2D.Double collisionPoint = model.getCollisionPoint(position, new Point2D.Double(Math.cos(ang), Math.sin(ang)));
            points.add(collisionPoint);
            return worm.getPosition().distance(collisionPoint) / 100;
        };
        input.set(0, 1 - Common.sigmoid(supplier.apply(angle))); // precision for close walls
        int oneSide = RAYS / 2 + 1;
        for (int j = -1; j <= 1; j += 2) {
            for (int i = 1; i < oneSide; i++) {
                double change = j * Math.PI * (i * i / ((double) oneSide * oneSide));
                input.set(i, 1 - Common.sigmoid(supplier.apply(angle + change))); // precision for close walls
            }
        }
        final Vector output = network.tick(input);
        final Double left = output.get(0);
        final Double right = output.get(1);

//        System.out.println("VECTOR: " + network.getState());
        worm.setDirection(Common.sigmoid((left - right)));
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

    public List<Point2D> getPoints() {
        return points;
    }

}
