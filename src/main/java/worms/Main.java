package worms;

import worms.controller.Controller;
import worms.model.Model;
import worms.view.GameWindow;

/**
 * @author Patrik Faistaver
 * @author Václav Blažej
 * @author Štěpán Plachý
 */
public class Main {

    public static void main(String[] args) {
        Settings settings = new Settings();
        Model model = new Model(settings);
        Controller controller = new Controller(model, settings);
        model.initialize();
        new GameWindow(model, controller, settings);
    }
}
