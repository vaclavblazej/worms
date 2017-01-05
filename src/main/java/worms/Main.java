package worms;

import worms.ai.evolution.EvolutionWrapper;
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
        EvolutionWrapper evolution = new EvolutionWrapper(controller, model, settings);
        model.initialize();
        new GameWindow(model, controller, settings, evolution);
    }
}
