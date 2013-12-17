/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package worms;

import java.awt.Color;
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

    /**
     * @param args the command line arguments
     */
    public void StartSelf (/*LinkedList<Elem> controls, etc.*/) {
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        LinkedList<Elem> controls = new LinkedList<>();
        
        controls.add(new Elem(65, 68, Color.RED));
        controls.add(new Elem(37, 39, Color.GREEN));
        //controls.add(new Elem(99, 105, Color.BLUE));
        
        gamePlane = new GamePanel(controls);
        gamePlane.setPreferredSize(new Dimension(800, 600));
        gamePlane.setFocusable(true);
        setTitle("Cervi 1.0");
        add(gamePlane);
        pack();

        gamePlane.startMoving();

        addKeyListener(new GameWindow());
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        gamePlane.setDirection(ke.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        gamePlane.setDirection(ke.getKeyCode(), false);
    }
}
