package worms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
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

    public MainWindow() {
        setTitle("Cervi 1.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(300, 400));

        JButton playButton = new JButton("Play!");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Main.initGame();
            }
        });

        mainPanel.add(playButton, BorderLayout.CENTER);
        add(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
