package worms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
    private static PlayerMenuPanel playerMenuPanel;
    private static final int MAXIMUM_PLAYERS = 6;

    public MainWindow() {
        setTitle("Cervi 1.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new FlowLayout());

        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(300, 400));

        JButton playButton = new JButton("Play!");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Main.initGame(playerMenuPanel.getControls());
            }
        });

        mainPanel.add(playButton);
        add(mainPanel);
        
        playerMenuPanel = new PlayerMenuPanel(MAXIMUM_PLAYERS);
        add(playerMenuPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
