package worms;

import worms.controller.Controller;
import worms.model.Model;
import worms.view.GameWindow;
import worms.menu.MainWindow;

/**
 *
 * @author Patrik Faistaver
 * @author Václav Blažej
 * @author Štěpán Plachý
 */
public class Main {

    private static MainWindow mainWindow;
    private static GameWindow gameWindow;
    private static Settings settings;
    private static Model model;
    private static Controller controller;

    public static void main(String[] args) {
        settings = new Settings();
        model = new Model(settings);
        controller = new Controller(model, settings);
        initMenu();
    }

    public static void initMenu() {
        mainWindow = new MainWindow(model, settings);
    }

    public static void initGame() {
        model.initialize();
        gameWindow = new GameWindow(model, controller, settings);
    }
}
