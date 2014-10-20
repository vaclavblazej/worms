package worms.model;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import worms.MyLine;
import worms.Settings;

/**
 *
 * @author Patrik Faistaver
 * @author Václav Blažej
 * @author Štěpán Plachý
 */
public final class Model {

    private final ArrayList<Player> players;
    private BufferedImage image;
    private final Point.Double origin;
    private final Settings settings;

    public Model(Settings settings) {
        this.settings = settings;
        players = new ArrayList<>(settings.getMaximumPlayerCount());
        origin = new Point.Double(settings.getWindowWidth() / 2, settings.getWindowHeight() / 2);
    }
    
    public void initialize(){
        final int playerCount = settings.getPlayerCount();
        for (int i = 0; i < playerCount; i++) {
            players.add(new Player(settings.getNames().get(i), settings.getColors().get(i)));
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
