package worms.ai;

import worms.model.Direction;
import worms.model.Model;
import worms.model.Player;
import worms.model.Worm;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Václav Blažej
 */
public class AiPreparedBrain extends AiBrain {

    public List<Point.Double> others;

    public AiPreparedBrain() {
        this.others = new ArrayList<>();
    }

    @Override
    public void think(Worm worm, Model model) {
        others.clear();
        final ArrayList<Player> players = model.getPlayers();
        for (Player player : players) {
            final Worm otherWorm = player.getWorm();
            if (otherWorm == worm) continue;
            others.add(otherWorm.getPosition());
            final Point2D.Double e = relativeVector(worm, otherWorm);
            System.out.println(e);
        }

        final int i = random.nextInt();
        if (i > 0) {
            worm.setDirection(Direction.RIGHT);
        } else {
            worm.setDirection(Direction.LEFT);
        }
    }

    Point.Double relativeVector(Worm origin, Worm object) {
        final Point2D.Double a = origin.getPosition();
        final Point2D.Double b = object.getPosition();
        final double angle = Math.toRadians(origin.getAngle());
        final double xx = (b.x - a.x) * Math.cos(angle);
        final double xy = (b.y - a.y) * Math.sin(angle);
        final double yy = -(b.y - a.y) * Math.cos(angle);
        final double yx = (b.x - a.x) * Math.sin(angle);
        final Point2D.Double d = new Point2D.Double(xx + xy, yy + yx);
        return d;
    }
}
