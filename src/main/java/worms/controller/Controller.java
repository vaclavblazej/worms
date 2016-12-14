package worms.controller;

import worms.MyLine;
import worms.Settings;
import worms.model.Model;
import worms.model.Player;
import worms.model.Worm;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Patrik Faistaver
 * @author Václav Blažej
 * @author Štěpán Plachý
 */
public class Controller {

    private final Model model;
    private final Settings settings;
    private final ArrayList<Player> playingPlayers;
    private int simulationDelay = 15;
    private Timer timer;
    private boolean GRAPHICS = true;
    private boolean stop = false;
    private long turn = 0;
    private int roundsLeft;
    private int tickNumber;

    public Controller(Model model, Settings settings) {
        this.model = model;
        this.settings = settings;
        this.timer = new Timer(simulationDelay, e -> tick());
        this.playingPlayers = new ArrayList<>();
        this.roundsLeft = 0;
        this.stop = true;

        this.reset();
    }

    private void tick() {
        prepareAllPlayers();
        simulateMovement();
        checkLostPlayers();
    }

    private void reset() {
        model.reset();
        for (Player player : model.getPlayers()) {
            player.setScore(0);
        }
    }

    public void startSimulation() {
        stop = false;
        startAllRounds();
    }

    public void startSimulationInThread() {
        roundsLeft = -1;
        stop = false;
        // break free of event thread
        Thread thread = new Thread(this::startAllRounds);
        thread.start();
    }

    public void endSimulation() {
        stop = true;
    }

    private void startAllRounds() {
        while (true) {
            if (shouldStop()) break;
            setupRound();
            reset();
            while (!gameEnded()) {
                if (GRAPHICS) {
                    try {
                        Thread.sleep(simulationDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                tick();
            }
        }
    }

    private boolean shouldStop() {
        if (roundsLeft-- == 0) {
            stop = true;
        }
        return stop;
    }

    private void setupRound() {
        turn++;
        tickNumber = 0;
        playingPlayers.clear();
        for (Player player : model.getPlayers()) {
            playingPlayers.add(player);
        }
    }

    public void evaluate(Player player) {
        if (stop) {
            ArrayList<Player> newPlayers = new ArrayList<>();
            newPlayers.add(player);
            model.setPlayers(newPlayers);
            roundsLeft = 1;
            this.reset();
            this.startSimulation();
        } else {
            throw new RuntimeException("simulation already runs");
        }
    }

    private void simulateMovement() {
        tickNumber++;
        for (Player player : playingPlayers) {
            Worm worm = player.getWorm();
            Point.Double position = worm.getPosition();
            double angle = worm.getAngle();
            Point.Double oldPosition = new Point.Double();
            oldPosition.x = position.x;
            oldPosition.y = position.y;
            position.x += Math.cos(angle);
            position.y -= Math.sin(angle);

            double rotation = worm.getDirection().getRotation(settings.getMoveAngleChange());
            angle += rotation;
            if (angle >= 2 * Math.PI) {
                angle -= 2 * Math.PI;
            } else if (angle < 0) {
                angle += 2 * Math.PI;
            }
            worm.setAngle(angle);

            worm.decreasePhaseShiftTimer(1);
            int time = worm.getPhaseShiftTimer();
            if (time < 0) {
                boolean shift = worm.isPhaseShifted();
                if (shift) {
                    worm.setPhaseShiftTimer(settings.getTimeBetweenPhaseShifts());
                } else {
                    worm.setPhaseShiftTimer(settings.getPhaseShiftDuration());
                }
                worm.setPhaseShift(!shift);
            }
            if (!worm.isPhaseShifted()) {
                int distance = 2;
                int x = (int) (position.x + distance * Math.cos(angle));
                int y = (int) (position.y - distance * Math.sin(angle));

                if (model.getMapColor(x, y) != Color.BLACK.getRGB()) {
                    player.setLost(true);
                }

                model.draw(new MyLine(oldPosition, position), player.getColor());
            }
        }
    }

    private void prepareAllPlayers() {
        // let each player brain work
        for (Player player : playingPlayers) {
            player.prepare(model);
        }
    }

    private void checkLostPlayers() {
        Iterator<Player> it = playingPlayers.iterator();
        while (it.hasNext()) {
            Player player = it.next();
            if (player.hasLost()) {
                player.setScore(tickNumber);
                it.remove();
            }
        }
    }

    private boolean gameEnded() {
        return playingPlayers.size() <= 0;
    }

    public void setSimulationDelay(int simulationDelay) {
        this.simulationDelay = simulationDelay;
    }

    public void setGRAPHICS(boolean GRAPHICS) {
        this.GRAPHICS = GRAPHICS;
    }

    public long getTurn() {
        return turn;
    }

    public int getRoundsLeft() {
        return roundsLeft;
    }

    public void setRoundsLeft(int roundsLeft) {
        this.roundsLeft = roundsLeft;
    }
}
