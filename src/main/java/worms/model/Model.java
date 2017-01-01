package worms.model;

import worms.Common;
import worms.MyLine;
import worms.Settings;
import worms.ai.AiBrain;
import worms.ai.AiNeuralBrain;
import worms.ai.ComputerPlayer;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Patrik Faistaver
 * @author Václav Blažej
 * @author Štěpán Plachý
 */
public final class Model {

    private final ArrayList<Player> players;
    private final Settings settings;
    private final Random random = new Random();
    private ArrayList<Scenario> scenarios;
    private BufferedImage image;

    public Model(Settings settings) {
        this.settings = settings;
        this.scenarios = new ArrayList<>();
        this.players = new ArrayList<>();
        setupScenarios();
    }

    private void setupScenarios() {
        this.scenarios.clear();
        Point.Double origin = new Point.Double(settings.getWindowWidth() / 2, settings.getWindowHeight() / 2);
        BufferedImage bufferedImage = new BufferedImage(settings.getWindowWidth() + 1, settings.getWindowHeight() + 1, BufferedImage.TYPE_INT_RGB);
//        Scenario basicScenario = new Scenario(origin, image = bufferedImage, Common.getRandomRadian());
        for (int i = 0; true; i++) {
            BufferedImage image = Common.loadImage("scenarios/" + (i + 1) + ".bmp");
            if (image == null) {
                break;
            }
            this.scenarios.add(new Scenario(origin, image, Common.getRandomRadian()));
        }
//        this.scenarios.add(basicScenario);
    }

    public void initialize() {
        final int playerCount = settings.getPlayerCount();

        for (int i = 0; i < playerCount; i++) {
            final Color color = Common.randomColor();
            AiBrain brain;
            brain = new AiNeuralBrain();
            players.add(new ComputerPlayer("CompAi " + i, color, brain));
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players.clear();
        this.players.addAll(players);
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
        int len = 4, jump = 2;
        pos.x += len * direction.x;
        pos.y -= len * direction.y;
        while (true) {
            final int mapColor = getMapColor((int) pos.x, (int) pos.y);
            if (mapColor != Color.BLACK.getRGB()) break;
            pos.x += jump * direction.x;
            pos.y -= jump * direction.y;
        }
//        lines.add(new Line2D.Double(point.x, point.y, pos.x, pos.y));
        return pos.distance(point);
    }

    public void reset(int scenarioId) {
        Scenario scenario = scenarios.get(scenarioId);
        loadScenario(scenario);
        double degreeChange = 2 * Math.PI / settings.getPlayerCount();
        double degree = scenario.degree;
        Point2D.Double origin = scenario.origin;
        for (Player player : players) {
            Worm worm = player.getWorm();
            worm.setPosition(origin.x, origin.y);
            worm.setAngle(degree);
            worm.setDirection(Direction.STRAIGHT);
            worm.setPhaseShiftTimer(settings.getPhaseShiftDuration());
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
        if (x >= image.getWidth() || x < 0 || y >= image.getHeight() || y < 0) {
            return Color.white.getRGB();
        }
        return image.getRGB(x, y);
    }

    public void loadScenario(Scenario scenario) {
        image = Common.deepCopy(scenario.image);
    }

    public int getNumberOfScenarios() {
        return scenarios.size();
    }
}
