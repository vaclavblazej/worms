package worms.view;

import worms.Settings;
import worms.controller.Controller;
import worms.model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

/**
 * @author Václav Blažej
 */
public class EvolutionView extends JPanel implements ActionListener {

    private static View gamePlane;
    final JLabel turn = new JLabel();
    final Timer timer = new Timer(1000, this);
    Controller controller;

    EvolutionView(Model model, Controller controller, Settings settings) {
        this.controller = controller;
        setVisible(true);
        setLayout(new GridLayout(6, 2));
        add(turn);
        final Checkbox skipGraphics = new Checkbox("Graphics: ", true);
        skipGraphics.addItemListener(e -> {
            switch (e.getStateChange()) {
                case ItemEvent.SELECTED:
                    controller.setGRAPHICS(true);
                    break;
                case ItemEvent.DESELECTED:
                    controller.setGRAPHICS(false);
                    break;
            }
        });
        add(skipGraphics);
        final JSlider speedSlider = new JSlider(2, 100, 15);
        speedSlider.addChangeListener(evt -> {
            controller.setSPEED_INITIAL(speedSlider.getValue());
        });
        add(speedSlider);
        final JSlider pauseSlider = new JSlider(0, 1000, 150);
        pauseSlider.addChangeListener(evt -> {
            controller.setPAUSE_INITIAL(pauseSlider.getValue());
        });
        add(pauseSlider);
        final JButton comp = new JButton("Hello button");
        add(comp);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        turn.setText("" + controller.getTurn());
    }
}
