package worms.view;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import worms.model.Model;
import worms.model.Player;

/**
 *
 * @author Pajcak
 */
public class PlayerSetupComponent extends JPanel {

    private JLabel label;
    private int left, right;
    private Color color;

    public PlayerSetupComponent(Model model, int playerId) {
        //Model model = view.getModel();
        Player player = model.getPlayer(playerId);
        setBackground(player.getColor());
        setLayout(new FlowLayout());
        label = new JLabel(player.getName());
        add(label);
    }
}
