package worms.model;

import worms.MyLine;
import worms.Settings;
import worms.ai.AiBrain;
import worms.ai.AiNeuralBrain;
import worms.ai.AiRandomBrain;
import worms.ai.ComputerPlayer;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
    private BufferedImage image;

    public Model(Settings settings) {
        this.settings = settings;
        players = new ArrayList<>(settings.getMaximumPlayerCount());
        origin = new Point.Double(settings.getWindowWidth() / 2, settings.getWindowHeight() / 2);
    }

    public void initialize() {
        final int playerCount = settings.getPlayerCount();
//        for (int i = 0; i < playerCount; i++) {
//            players.add(new HumanPlayer(settings.getNames().get(i), settings.getColors().get(i)));
//        }
        final Random random = new Random();
        final Supplier<Float> func = () -> ((random.nextFloat() % 0.7f) + 0.3f) % 1;

        for (int i = 0; i < playerCount; i++) {
            final Color color = Color.getHSBColor(func.get(), func.get(), func.get());
            AiBrain brain;
            if (i == 0) brain = new AiNeuralBrain();
            else brain = new AiRandomBrain();
            players.add(new ComputerPlayer("CompAi " + i, color, brain));
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

    public void changeDirection(Integer playerId, Direction direction) {
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
        double result = 0;
        while (true) {
            final int mapColor = getMapColor((int) pos.x, (int) pos.y);
            System.out.println("mapColor: " + mapColor);
            if (mapColor != 1) break;
            pos.x += direction.x;
            pos.y += direction.y;
        }
        return result;
    }

    public void reset() {
        image = new BufferedImage(settings.getWindowWidth() + 1, settings.getWindowHeight() + 1, BufferedImage.TYPE_INT_RGB);

        int degreeChange = 360 / settings.getPlayerCount();
        int degree = 0;
        for (Player player : players) {
            Worm worm = player.getWorm();
            worm.setPosition(origin.x, origin.y);
            worm.setAngle(degree);
            worm.setDirection(Direction.STRAIGHT);
            player.setLost(false);
            degree += degreeChange;
        }
    }

    public void draw(MyLine line, Color color) {
        line.draw(image, color);
    }

    public int getMapColor(int x, int y) {
        return image.getRGB(x, y);
    }
}
