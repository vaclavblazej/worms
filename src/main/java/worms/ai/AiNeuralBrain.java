package worms.ai;

import worms.ai.neuralnet.NeuralNetwork;
import worms.ai.neuralnet.Vector;
import worms.model.Direction;
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

    public List<Point.Double> others;
    private NeuralNetwork network;

    public AiNeuralBrain() {
        this.others = new ArrayList<>();
        this.network = new NeuralNetwork();
        network.prepare(2, 3, 1);
    }

    @Override
    public void think(Worm worm, Model model) {
        others.clear();
        final Point2D.Double position = worm.getPosition();
        Vector input = new Vector(2);
        final Vector output = network.tick(input);
        final Double result = output.get(0);
//        model.
//        final ArrayList<Player> players = model.getPlayers();
//        for (Player player : players) {
//            final Worm otherWorm = player.getWorm();
//            if (otherWorm == worm) continue;
//            others.add(otherWorm.getPosition());
//            final Point2D.Double e = relativeVector(worm, otherWorm);
//            System.out.println(e);
//        }

        final int i = random.nextInt();
        if (result > 0.6) {
            worm.setDirection(Direction.RIGHT);
        } else if (result > 0.4) {
            worm.setDirection(Direction.STRAIGHT);
        } else {
            worm.setDirection(Direction.LEFT);
        }
    }

    Point.Double relativeVector(Worm origin, Worm object) {
        final Point2D.Double a = origin.getPosition();
        final Point2D.Double b = object.getPosition();
        final double angle = origin.getAngle();
        final double xx = (b.x - a.x) * Math.cos(angle);
        final double xy = (b.y - a.y) * Math.sin(angle);
        final double yy = -(b.y - a.y) * Math.cos(angle);
        final double yx = (b.x - a.x) * Math.sin(angle);
        final Point2D.Double d = new Point2D.Double(xx + xy, yy + yx);
        return d;
    }
}
