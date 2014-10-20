package worms.view;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import worms.Settings;
import worms.model.Player;

/**
 *
 * @author Patrik Faistaver
 * @author Václav Blažej
 * @author Štěpán Plachý
 */
public class PlayerSetupComponent extends JPanel {

    private JLabel label;
    private int left, right;
    private Color color;

    public PlayerSetupComponent(Settings settings, int playerId) {
        setBackground(settings.getColors().get(playerId));
        setLayout(new FlowLayout());
        label = new JLabel(settings.getNames().get(playerId));
        add(label);
    }
}
