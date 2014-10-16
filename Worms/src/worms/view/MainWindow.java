package worms.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import worms.Main;
import worms.Settings;
import worms.controller.Controller;
import worms.model.Model;

/**
 *
 * @author Pajcak & Venca
 */
public class MainWindow extends JFrame {

    private static JPanel mainPanel;
    private static GameWindow gameWindow;
    private static PlayerMenuPanel playerMenuPanel;
    private static final int MAXIMUM_PLAYERS = 5;

    public MainWindow(Model model, Settings settings) {
        setTitle("Cervi 1.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        JButton playButton = new JButton("Play!");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Main.initGame();
            }
        });

        add(playButton, BorderLayout.NORTH);

        playerMenuPanel = new PlayerMenuPanel(model, settings);
        add(playerMenuPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
