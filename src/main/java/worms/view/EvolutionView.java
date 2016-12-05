package worms.view;

import worms.Settings;
import worms.controller.Controller;
import worms.model.Model;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @author Václav Blažej
 */
public class EvolutionView extends JPanel {

    private static View gamePlane;
    final JLabel turn = new JLabel();

    public EvolutionView(Model model, Controller controller, Settings settings) {
        setVisible(true);
        setLayout(new GridLayout(6,2));
        add(turn);
        final Checkbox skipGraphics = new Checkbox();
        add(skipGraphics);
        final JSlider speedSlider = new JSlider(2, 100, 15);
        speedSlider.addChangeListener(evt -> {
            controller.setSPEED(speedSlider.getValue());
        });
        add(speedSlider);
        final JSlider pauseSlider = new JSlider(0, 1000, 150);
        pauseSlider.addChangeListener(evt -> {
            controller.setPAUSE(pauseSlider.getValue());
        });
        add(pauseSlider);
        final JButton comp = new JButton("Hello button");
        add(comp);
    }
}
