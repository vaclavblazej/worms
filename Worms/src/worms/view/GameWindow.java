package worms.view;

import java.awt.Dimension;
import javax.swing.JFrame;
import worms.Settings;
import worms.controller.Controller;
import worms.controller.Input;
import worms.model.Model;

/**
 *
 * @author Pajcak & Venca
 */
public class GameWindow extends JFrame {

    private static View gamePlane;

    public GameWindow(Model model, Controller controller, Settings settings) {
        super("Cervi 1.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        gamePlane = new View(model, controller);
        gamePlane.setPreferredSize(new Dimension(800, 600));
        gamePlane.setFocusable(true);
        add(gamePlane);
        pack();

        setFocusable(true);
        
        Input input = new Input(model, settings);
        this.addKeyListener(input);
        gamePlane.addKeyListener(input);
    }
}
