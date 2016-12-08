package worms.model;

import worms.MyLine;
import worms.Settings;
import worms.ai.AiBrain;
import worms.ai.AiNeuralBrain;
import worms.ai.ComputerPlayer;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.function.Supplier;

/**
 * @author Patrik Faistaver
 * @author Václav Blažej
 * @author Štěpán Plachý
 */
public final class Model {

    private final ArrayList<Player> players;
    private final Point.Double origin;
    private final Settings settings;
    private final ArrayList<Line2D> lines;
    private final Random random = new Random();
    private final Supplier<Float> func = () -> ((random.nextFloat() % 0.7f) + 0.3f) % 1;
    private BufferedImage image;

    public Model(Settings settings) {
        this.settings = settings;
        players = new ArrayList<>();
        origin = new Point.Double(settings.getWindowWidth() / 2, settings.getWindowHeight() / 2);
        lines = new ArrayList<>();
    }

    public void initialize() {
        final int playerCount = settings.getPlayerCount();
//        for (int i = 0; i < playerCount; i++) {
//            players.add(new HumanPlayer(settings.getNames().get(i), settings.getColors().get(i)));
//        }

        for (int i = 0; i < playerCount; i++) {
            final Color color = Color.getHSBColor(func.get(), func.get(), func.get());
            AiBrain brain;
//            if (i == 0) {
            brain = new AiNeuralBrain();
//            } else {
//                brain = new AiRandomBrain();
//            }
            players.add(new ComputerPlayer(i, "CompAi " + i, color, brain));
        }
        reset();
    }

    public Point2D.Double getOrigin() {
        return origin;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public void changeDirection(Integer playerId, double direction) {
        players.get(playerId).getWorm().setDirection(direction);
    }

    public BufferedImage getImage() {
        return image;
    }

    public double getDistance(Point2D.Double point, Point2D.Double direction) {
        Point2D.Double pos = new Point2D.Double(point.x, point.y);
        double size = direction.distance(0, 0);
        direction.x /= size;
        direction.y /= size;
        int len = 4;
        pos.x += len * direction.x;
        pos.y -= len * direction.y;
        while (true) {
            final int mapColor = getMapColor((int) pos.x, (int) pos.y);
            if (mapColor != Color.BLACK.getRGB()) break;
            pos.x += direction.x;
            pos.y -= direction.y;
        }
        lines.add(new Line2D.Double(point.x, point.y, pos.x, pos.y));
        return pos.distance(point);
    }

    public void reset() {
        image = new BufferedImage(settings.getWindowWidth() + 1, settings.getWindowHeight() + 1, BufferedImage.TYPE_INT_RGB);

        double degreeChange = 2 * Math.PI / settings.getPlayerCount();
        double degree = 2 * Math.PI * random.nextDouble();
        for (Player player : players) {
            Worm worm = player.getWorm();
            worm.setPosition(origin.x, origin.y);
            worm.setAngle(degree);
            worm.setDirection(Direction.STRAIGHT);
            player.setLost(false);
            degree += degreeChange;
            if (player instanceof ComputerPlayer) {
                ComputerPlayer player1 = (ComputerPlayer) player;
                player1.getBrain().reset();
            }
        }
    }

    public void draw(MyLine line, Color color) {
        line.draw(image, color);
    }

    public int getMapColor(int x, int y) {
        if (x > settings.getWindowWidth() || x <= 0 || y > settings.getWindowHeight() || y <= 0) {
            return Color.white.getRGB();
        }
        return image.getRGB(x, y);
    }

    public void evolve(int winnerId) {
        Player winnerPlayer = players.get(winnerId);
        if (!(winnerPlayer instanceof ComputerPlayer)) return;
//        AiNeuralBrain brain = (AiNeuralBrain) ((ComputerPlayer) winnerPlayer).getBrain();

        for (int i = 0; i < players.size(); i++) {
            if (winnerId != i && players.get(i) instanceof ComputerPlayer) {
                AiBrain brain1 = ((ComputerPlayer) players.get(i)).getBrain();
                if (!(brain1 instanceof AiNeuralBrain)) continue;
//                ((AiNeuralBrain) brain1).setNetwork(brain.getNetwork().mutate());
                ((AiNeuralBrain) brain1).setNetwork(((AiNeuralBrain) brain1).getNetwork().mutate());
            }
        }
        Collections.shuffle(players); // population has to accommodate for any start point
    }

    public ArrayList<Line2D> getLines() {
        return new ArrayList<>(lines);
    }

    public void clearLines() {
        lines.clear();
    }
}
