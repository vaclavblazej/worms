package worms.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import worms.Settings;
import worms.model.Direction;
import worms.model.Model;

/**
 *
 * @author Václav Blažej
 */
public class Input implements KeyListener {

    private Map<Integer, Integer> leftMap;
    private Map<Integer, Integer> rightMap;
    private ArrayList<Boolean> leftPressed;
    private ArrayList<Boolean> rightPressed;
    private Model model;

    public Input(Model model, Settings settings) {
        this.model = model;
        int playerCount = settings.getPlayerCount();
        leftPressed = new ArrayList<>(playerCount);
        rightPressed = new ArrayList<>(playerCount);
        for (int i = 0; i < playerCount; i++) {
            leftPressed.add(false);
            rightPressed.add(false);
        }

        leftMap = new HashMap<>(playerCount);
        ArrayList<Integer> leftKeys = settings.getLeftMap();
        for (int i = 0; i < leftKeys.size(); i++) {
            leftMap.put(leftKeys.get(i), i);
        }
        rightMap = new HashMap<>(playerCount);
        ArrayList<Integer> rightKeys = settings.getRightMap();
        for (int i = 0; i < rightKeys.size(); i++) {
            rightMap.put(rightKeys.get(i), i);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void computeDirection(Integer playerId) {
        int direction = 0;
        if (leftPressed.get(playerId)) {
            direction--;
        }
        if (rightPressed.get(playerId)) {
            direction++;
        }
        switch (direction) {
            case -1:
                model.changeDirection(playerId, Direction.LEFT);
                break;
            case 0:
                model.changeDirection(playerId, Direction.STRAIGHT);
                break;
            case 1:
                model.changeDirection(playerId, Direction.RIGHT);
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Integer playerId = leftMap.get(e.getKeyCode());
        if (playerId != null) {
            leftPressed.set(playerId, true);
        } else {
            playerId = rightMap.get(e.getKeyCode());
            if (playerId != null) {
                rightPressed.set(playerId, true);
            }
        }
        if (playerId != null) {
            computeDirection(playerId);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Integer playerId = leftMap.get(e.getKeyCode());
        if (playerId != null) {
            leftPressed.set(playerId, false);
        } else {
            playerId = rightMap.get(e.getKeyCode());
            if (playerId != null) {
                rightPressed.set(playerId, false);
            }
        }
        if (playerId != null) {
            computeDirection(playerId);
        }
    }
}
