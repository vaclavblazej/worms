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
    private final int SPEED_INITIAL = 15; // 15 original, lower is faster
    private Timer timer, delay;
    private boolean GRAPHICS = true;
    private boolean RESTART = false;
    private boolean stop = false;
    private long turn = 0;
    private int evolutionIterations;

    public Controller(Model model, Settings settings) {
        this.model = model;
        this.settings = settings;
        this.timer = new Timer(SPEED_INITIAL, e -> tick());
        this.delay = new Timer(10, e -> startGame());
        this.playingPlayers = new ArrayList<>();
        this.evolutionIterations = 10;

        model.reset();
    }

    private void tick() {
        prepareAllPlayers();
        simulateMovement();
        checkLostPlayers();
        if (gameEnded()) {
            timer.stop();
            reset();
            delay.start();
        }
    }

    private void reset() {
        model.reset();
        if (turn % evolutionIterations == 0) {
            System.out.print("Evolve, scores:");
            int winnerId = 0, mx = -1;
            for (Player player : model.getPlayers()) {
                System.out.print(" " + player.getScore());
                if (player.getScore() > mx) {
                    mx = player.getScore();
                    winnerId = player.getId();
                }
            }
            System.out.println();
            model.evolve(winnerId);
            for (Player player : model.getPlayers()) {
                player.setScore(0);
            }
        }
    }

    private void quickTick() {
        prepareAllPlayers();
        simulateMovement();
        checkLostPlayers();
        if (gameEnded()) {
            reset();
            RESTART = true;
        }
    }

    public void startSimulation() {
        stop = false;
        delay.start();
    }

    public void endSimulation() {
        stop = true;
    }

    private void startGame() {
        delay.stop();
        if (GRAPHICS) {
            setupRound();
            if (!shouldStop()) {
                timer.start();
            }
        } else {
            // break free of event thread
            Thread thread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    while (true) {
                        if (GRAPHICS || shouldStop()) break;
                        setupRound();
                        RESTART = false;
                        while (!RESTART) {
                            quickTick();
                        }
                    }
                    delay.start();
                }
            };
            thread.start();
        }
    }

    private boolean shouldStop() {
        return stop;
    }

    private void setupRound() {
        turn++;
        playingPlayers.clear();
        for (Player player : model.getPlayers()) {
            playingPlayers.add(player);
        }
    }

    private void simulateMovement() {
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
                it.remove();
                for (Player otherPlayer : playingPlayers) {
                    otherPlayer.incrementScore();
                }
            }
        }
    }

    private boolean gameEnded() {
        return playingPlayers.size() <= 0;
    }

    public void setSPEED_INITIAL(int SPEED_INITIAL) {
        timer.setDelay(SPEED_INITIAL);
    }

    public void setGRAPHICS(boolean GRAPHICS) {
        this.GRAPHICS = GRAPHICS;
    }

    public long getTurn() {
        return turn;
    }

    public int getEvolutionIterations() {
        return evolutionIterations;
    }

    public void setEvolutionIterations(int evolutionIterations) {
        this.evolutionIterations = evolutionIterations;
    }
}
