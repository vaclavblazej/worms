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
    private final int PAUSE_INITIAL = 100;
    private Timer timer, delay;
    private boolean GRAPHICS = true;
    private boolean RESTART = false;
    private long turn = 0;
    private int numbers = 1;

    public Controller(Model model, Settings settings) {
        this.model = model;
        this.settings = settings;
        this.timer = new Timer(SPEED_INITIAL, e -> tick());
        this.delay = new Timer(PAUSE_INITIAL, e -> startGame());
        this.playingPlayers = new ArrayList<>();

        model.reset();
    }

    private void tick() {
        prepareAllPlayers();
        simulateMovement();
        checkLostPlayers();
        // restart game if needed
        if (gameEnded()) {
            timer.stop();
            model.reset();
            delay.start();
        }
    }

    private void quickTick() {
        prepareAllPlayers();
        simulateMovement();
        checkLostPlayers();
        if (gameEnded()) {
            model.reset();
            RESTART = true;
        }
    }

    public void startSession(int numbers) {
        this.numbers = numbers;
        delay.start();
    }

    private void startGame() {
        delay.stop();
        if (GRAPHICS) {
            setupRound();
            if (isValidIteration()) {
                timer.start();
            }
        } else {
            // break free of event thread
            Thread thread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    while (true) {
                        if (GRAPHICS || !isValidIteration()) break;
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

    private boolean isValidIteration() {
        return numbers-- > 0;
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

            switch (worm.getDirection()) {
                case LEFT:
                    angle += settings.getMoveAngleChange();
                    break;
                case RIGHT:
                    angle -= settings.getMoveAngleChange();
                    break;
                case STRAIGHT:
                    break;
            }
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
        return playingPlayers.size() <= 1;
    }

    public void setSPEED_INITIAL(int SPEED_INITIAL) {
        timer.setDelay(SPEED_INITIAL);
    }

    public void setPAUSE_INITIAL(int PAUSE_INITIAL) {
        delay.setDelay(PAUSE_INITIAL);
    }

    public void setGRAPHICS(boolean GRAPHICS) {
        this.GRAPHICS = GRAPHICS;
    }

    public long getTurn() {
        return turn;
    }
}
