package worms;

import java.awt.Color;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Pajcak
 */
public class PlayerSetupComponent extends JPanel {

    private JLabel label;
    private JComboBox<String> box;

    public PlayerSetupComponent() {
        setBackground(Color.yellow);
        label = new JLabel("Player: ");
        add(label);
        box = new JComboBox<>();
        add(box);
    }

    public Elem getPlayerControls() {
        //todo
        return null;
    }
}
