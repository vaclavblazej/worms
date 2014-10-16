package worms;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Václav Blažej
 */
public final class Settings implements Serializable {

    private boolean loaded = false;
    private int playerCount;
    private int timeBetweenPhaseShifts;
    private int phaseShiftDuration;
    private int phaseShiftHeadsUpTime;
    private int windowWidth;
    private int windowHeight;
    private final ArrayList<String> names;
    private final ArrayList<Integer> leftMap;
    private final ArrayList<Integer> rightMap;
    private final ArrayList<Color> colors;
    //todo String languageId;

    public Settings() {
        playerCount = 0;
        timeBetweenPhaseShifts = 20;
        phaseShiftDuration = 5;
        phaseShiftHeadsUpTime = 2;
        windowWidth = 800;
        windowHeight = 600;
        names = new ArrayList<>();
        leftMap = new ArrayList<>();
        rightMap = new ArrayList<>();
        colors = new ArrayList<>();
        addPlayer("Player 1: < >", 65, 68, Color.RED);
        addPlayer("Player 2: A D", 37, 39, Color.GREEN);
        addPlayer("Player 3: 3 9", 99, 105, Color.CYAN);
        addPlayer("Player 4: J L", 74, 76, Color.PINK);
        addPlayer("Player 5: V B", 86, 66, Color.ORANGE);
        loaded = true;
    }

    public void addPlayer(String name, int leftKey, int rightKey, Color color) {
        names.add(name);
        leftMap.add(leftKey);
        rightMap.add(rightKey);
        colors.add(color);
        playerCount++;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public int getTimeBetweenPhaseShifts() {
        return timeBetweenPhaseShifts;
    }

    public void setTimeBetweenPhaseShifts(int timeBetweenPhaseShifts) {
        this.timeBetweenPhaseShifts = timeBetweenPhaseShifts;
    }

    public int getPhaseShiftDuration() {
        return phaseShiftDuration;
    }

    public void setPhaseShiftDuration(int phaseShiftDuration) {
        this.phaseShiftDuration = phaseShiftDuration;
    }

    public int getPhaseShiftHeadsUpTime() {
        return phaseShiftHeadsUpTime;
    }

    public void setPhaseShiftHeadsUpTime(int phaseShiftHeadsUpTime) {
        this.phaseShiftHeadsUpTime = phaseShiftHeadsUpTime;
    }

    public ArrayList<Integer> getLeftMap() {
        return leftMap;
    }

    public ArrayList<Integer> getRightMap() {
        return rightMap;
    }

    public ArrayList<Color> getColors() {
        return colors;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

}
