/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package worms;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Pajcak & Venca
 */
public class MainWindow extends JFrame {
    private static JPanel mainPanel;
    private static GameWindow gameWindow;
   
    public void showMe() {
        setTitle("Cervi 1.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(300, 400));
        
        JButton batn = new JButton("blabla!");
        batn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    dispose();
                    gameWindow = new GameWindow();
                    gameWindow.setFocusable(true);
                    gameWindow.StartSelf();  
                }
        });

        mainPanel.add(batn);
        
        add(mainPanel);
        pack();
        setVisible(true);
    }
}
