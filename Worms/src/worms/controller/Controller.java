package worms.controller;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import worms.MyLine;
import worms.Settings;
import worms.model.Model;
import worms.model.Player;
import worms.model.Worm;
import worms.view.View;

/**
 *
 * @author Patrik Faistaver
 * @author Václav Blažej
 * @author Štěpán Plachý
 */
public class Controller implements ActionListener {

    private final Model model;
    private final Settings settings;
    private final Timer timer;
    private final ArrayList<Player> playingPlayers;

    public Controller(Model model, Settings settings) {
        this.model = model;
        this.settings = settings;
        this.timer = new Timer(15, this);
        this.playingPlayers = new ArrayList<>();

        model.reset();
    }

    public void tick() {
        for (Player player : playingPlayers) {
            Worm worm = player.getWorm();
            Point.Double position = worm.getPosition();
            int angle = worm.getAngle();
            Point.Double oldPosition = new Point.Double();
            oldPosition.x = position.x;
            oldPosition.y = position.y;
            position.x += Math.cos(Math.toRadians(angle));
            position.y -= Math.sin(Math.toRadians(angle));

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
            if (angle >= 360) {
                angle -= 360;
            } else if (angle < 0) {
                angle += 360;
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
                int x = (int) (position.x + distance * Math.cos(Math.toRadians(angle)));
                int y = (int) (position.y - distance * Math.sin(Math.toRadians(angle)));

                if (x > settings.getWindowWidth() || x <= 0
                        || y > settings.getWindowHeight() || y <= 0
                        || model.getMapColor(x, y) != Color.BLACK.getRGB()) {
                    player.setLost(true);
                }

                model.draw(new MyLine(oldPosition, position), player.getColor());
            }
        }
    }

    public void startGame() {
        playingPlayers.clear();
        for (Player player : model.getPlayers()) {
            playingPlayers.add(player);
        }
        timer.start();
    }

    public boolean gameEnded() {
        return playingPlayers.size() <= 1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tick();
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
        if (gameEnded()) {
            timer.stop();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
            model.reset();
            startGame();
        }
    }
}
