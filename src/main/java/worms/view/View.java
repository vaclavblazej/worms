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
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Iterator;

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
        this.setPreferredSize(new Dimension(800, 600));
        this.setFocusable(true);
        this.timer = new Timer(40, this);
        SwingUtilities.invokeLater(timer::start);
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
            g.setColor(player.getColor());
            String str = ""+player.getScore();
            g.drawString(str, pos, 10);
            double length = getFontMetrics(getFont()).getStringBounds(str, getGraphics()).getWidth();
            pos += length + 4;
        }
        g.setColor(Color.RED);
//        for (Line2D line2D : model.getLines()) {
//            g.drawLine((int) line2D.getX1(), (int) line2D.getY1(), (int) line2D.getX2(), (int) line2D.getY2());
//        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
