package worms.view;

import worms.Settings;
import worms.ai.AiBrain;
import worms.ai.AiNeuralBrain;
import worms.ai.ComputerPlayer;
import worms.controller.Controller;
import worms.model.Model;
import worms.model.Player;
import worms.model.Worm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

/**
 * @author Patrik Faistaver
 * @author Václav Blažej
 * @author Štěpán Plachý
 */
public class View extends JPanel implements ActionListener {

    private final Timer timer;
    private final Model model;
    private final Controller controller;
    private final Settings settings;

    public View(Model model, Controller controllerArg, Settings settings) {
        this.model = model;
        this.controller = controllerArg;
        this.settings = settings;
        this.timer = new Timer(40, this);
        // when invokeLater is not used, first game is spoiled by bug
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                SwingUtilities.invokeLater(() -> {
                    timer.start();
                    controller.startSession();
                });
            }
        };
        thread.run();
    }

    public Model getModel() {
        return model;
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        Graphics2D g = (Graphics2D) grphcs;
        g.drawImage(model.getImage(), null, this);
        for (Player player : model.getPlayers()) {
            Worm worm = player.getWorm();
            Point.Double position = worm.getPosition();
            if (worm.isPhaseShifted()) {
                g.setColor(Color.white);
            } else {
                g.setColor(player.getColor());
            }
            g.drawOval((int) position.x - 1, (int) position.y - 1, 2, 2);
        }
        int pos = 10;
        for (Player player : model.getPlayers()) {
            if (player instanceof ComputerPlayer) {
                final AiBrain br = ((ComputerPlayer) player).getBrain();
                if (br instanceof AiNeuralBrain) {
                    final AiNeuralBrain brain = (AiNeuralBrain) br;
                    final Point2D.Double position = player.getWorm().getPosition();
                    final double distance = position.distance(0, 0);
                    g.setColor(Color.getHSBColor(1f - Math.min((float) distance / 800f, 1f), 1f, 1f));
                    for (Point2D.Double other : brain.others) {
                        g.drawLine((int) position.x, (int) position.y, (int) other.x, (int) other.y);
                    }
                }
            }
            g.setColor(player.getColor());
            g.drawString("" + player.getScore(), pos, 10);
            pos += 20;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
