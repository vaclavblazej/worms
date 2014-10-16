package worms.view;

import java.awt.Color;
import worms.model.Worm;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import worms.controller.Controller;
import worms.model.Model;
import worms.model.Player;

/**
 *
 * @author Pajcak & Venca
 */
public class View extends JPanel implements ActionListener {

    private static final int STEPS_IN_TICK = 3;
    private Timer timer;
    private Point origin;
    private Model model;
    private Controller controller;

    public Model getModel() {
        return model;
    }

    public View(Model model, Controller controllerArg) {
        this.model = model;
        this.controller = controllerArg;
        this.timer = new Timer(40, this);
        // when invokeLater is not used, first game is spoiled by bug
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                timer.start();
                controller.startGame();
            }
        });
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
