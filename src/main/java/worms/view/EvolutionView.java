package worms.view;

import worms.Settings;
import worms.controller.Controller;
import worms.model.Model;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
    private final Timer refreshGraphics = new Timer(200, this);
    int iterations = 0;
    Controller controller;

    EvolutionView(Model model, Controller controller, Settings settings) {
        this.controller = controller;
        setVisible(true);
        setLayout(new GridLayout(12, 2));
        add(turn);
        final Checkbox showRays = new Checkbox("Show rays: ", true);
        showRays.addItemListener(e -> {
//            model.(e.getStateChange() == ItemEvent.SELECTED);
        });
        add(showRays);
        final Checkbox skipGraphics = new Checkbox("Graphics: ", true);
        skipGraphics.addItemListener(e -> {
            controller.setGRAPHICS(e.getStateChange() == ItemEvent.SELECTED);
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
        final JButton comp = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("run " + iterations + " iterations");
                controller.startSession(iterations);
            }
        });
        add(comp);
        final JSlider iterationSlider = new JSlider(1, 10000, 500);
        iterationSlider.addChangeListener(evt -> {
            iterations = iterationSlider.getValue();
            comp.setText("Start " + iterations + " iterations");
        });
        ChangeEvent ce = new ChangeEvent(iterationSlider);
        for (ChangeListener cl : iterationSlider.getChangeListeners()) {
            cl.stateChanged(ce);
        }
        add(iterationSlider);
        refreshGraphics.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        turn.setText("" + controller.getTurn());
    }
}
