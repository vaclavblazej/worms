package worms;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import javax.swing.JFrame;

/**
 *
 * @author Pajcak & Venca
 */
public class GameWindow extends JFrame implements KeyListener {

    private static GamePanel gamePlane;

    public GameWindow(LinkedList<Elem> controls) {
        super("Cervi 1.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        gamePlane = new GamePanel(controls);
        gamePlane.setPreferredSize(new Dimension(800, 600));
        gamePlane.setFocusable(true);
        add(gamePlane);
        pack();

        gamePlane.startMoving();
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        System.out.println(ke.getKeyCode());
        gamePlane.setDirection(ke.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        gamePlane.setDirection(ke.getKeyCode(), false);
    }
}
