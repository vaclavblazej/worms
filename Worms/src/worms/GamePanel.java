/*
 * To change this template, choose Tools | Templaqtes
 * and open the template in the editor.
 */
package worms;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Pajcak & Venca
 */
public class GamePanel extends JPanel implements ActionListener {

    private ArrayList<Worm> worms;
    private Map<Integer, Worm> leftMap;
    private Map<Integer, Worm> rightMap;
    private LinkedList<Elem> controls;
    private BufferedImage image;
    private Timer timer;
    private static final int STEPS_IN_TICK = 2;

    public void setDirection(int keyNum, boolean val) {
        if (leftMap.get(keyNum) != null) {
            leftMap.get(keyNum).setLeft(val);
        } else if (rightMap.get(keyNum) != null) {
            rightMap.get(keyNum).setRight(val);
        }
    }

    public void startMoving() {
        timer.start();
    }

    public GamePanel(LinkedList<Elem> controls) {
        this.controls = controls;
        leftMap = new HashMap<>();
        rightMap = new HashMap<>();
        worms = new ArrayList<>();
        timer = new Timer(10, this);
        init();
    }

    public void init() {
        image = new BufferedImage(801, 601, BufferedImage.TYPE_INT_RGB);
        worms.clear();
        leftMap.clear();
        rightMap.clear();
        int degreeChange = 360 / controls.size();
        int degree = 0;
        for (Elem elem : controls) {
            Worm worm = new Worm(300, 350, degree, elem.color);
            worms.add(worm);
            leftMap.put(elem.a, worm);
            rightMap.put(elem.b, worm);
            degree += degreeChange;
        }
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        Graphics2D g = (Graphics2D) grphcs;
        g.drawImage(image, null, this);
        for (Worm worm : worms) {
            worm.draw(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        for (int i = 0; i < STEPS_IN_TICK; i++) {
            for (Worm worm : worms) {
                worm.tick(image);
            }
            for (Worm worm : worms) {
                if (worm.isCrash() == true) {
                    worms.remove(worm);
                    break;
                }
            }
            if (worms.size() <= 1) { // if only 1 player lasts
                timer.stop();
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                init();
                startMoving();
            }
            repaint();
        }
    }
}
