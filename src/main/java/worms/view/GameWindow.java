package worms.view;

import java.awt.*;
import javax.swing.*;

import worms.Settings;
import worms.controller.Controller;
import worms.controller.Input;
import worms.model.Model;

/**
 *
 * @author Patrik Faistaver
 * @author Václav Blažej
 * @author Štěpán Plachý
 */
public class GameWindow extends JFrame {

    private static View gamePlane;

    public GameWindow(Model model, Controller controller, Settings settings) {
        super("Cervi 1.0");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLayout(new FlowLayout());

        gamePlane = new View(model, controller, settings);
        gamePlane.setPreferredSize(new Dimension(800+200, 600));
        gamePlane.setFocusable(true);
        add(gamePlane);
        final EvolutionView evolutionView = new EvolutionView(model, controller, settings);
        add(evolutionView);
        pack();

        setFocusable(true);

        Input input = new Input(model, settings);
        this.addKeyListener(input);
        gamePlane.addKeyListener(input);
    }
}
