package worms;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author Pajcak & Venca
 */
public class GamePanel extends JPanel implements ActionListener {

    private static final int STEPS_IN_TICK = 3;
    private ArrayList<Worm> worms;
    private ArrayList<Worm> activeWorms;
    private Map<Integer, Worm> leftMap;
    private Map<Integer, Worm> rightMap;
    private LinkedList<Elem> controls;
    private BufferedImage image;
    private Timer timer;
    private ArrayList<Score> scores;
    private Point origin;

    public GamePanel(LinkedList<Elem> controls) {
        this.controls = controls;
        int size = controls.size();
        leftMap = new HashMap<>(size);
        rightMap = new HashMap<>(size);
        worms = new ArrayList<>(size);
        activeWorms = new ArrayList<>(size);
        scores = new ArrayList<>(size);
        timer = new Timer(20, this);
        // when invokeLater is not used, first game is spoiled by bug
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });
    }

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

    public void init() {
        worms.clear();
        leftMap.clear();
        rightMap.clear();
        int degreeChange = 360 / controls.size();
        int degree = 0;
        origin = new Point(getWidth() / 2, getHeight() / 2);
        for (Elem elem : controls) {
            Worm worm = new Worm(origin.x, origin.y, degree, elem.color);
            worms.add(worm);
            scores.add(worm.getScore());
            leftMap.put(elem.left, worm);
            rightMap.put(elem.right, worm);
            degree += degreeChange;
        }
        reset();
    }

    public void reset() {
        image = new BufferedImage(801, 601, BufferedImage.TYPE_INT_RGB);
        activeWorms.clear();
        for (Worm worm : worms) {
            worm.reset(origin.x, origin.y);
            activeWorms.add(worm);
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
        int pos = 10;
        for (Score score : scores) {
            g.setColor(score.color);
            g.drawString("" + score.points, pos, 10);
            pos += 20;
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        for (int i = 0; i < STEPS_IN_TICK; i++) {
            // TODO - should be tie when nobody lives for few millisecond more than others
            for (Worm worm : activeWorms) {
                worm.tick(image);
            }
            for (Worm worm : activeWorms) {
                if (worm.isCrash() == true) {
                    activeWorms.remove(worm);
                    break;
                }
            }
            if (activeWorms.size() <= 1) { // game ended
                if(activeWorms.size() == 1){ // only 1 player lasts
                    activeWorms.get(0).increaseScore();
                }
                timer.stop();
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                reset();
                startMoving();
            }
            repaint();
        }
    }
}
