package worms;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Pajcak
 */
public class PlayerSetupComponent extends JPanel {

    private JLabel label;
    private int left, right;
    private Color color;

    public PlayerSetupComponent(String str, int left, int right, Color color) {
        setBackground(color);
        this.left = left;
        this.right = right;
        this.color = color;
        setLayout(new FlowLayout());
        label = new JLabel(str);
        add(label);
    }

    public Elem getPlayerControls() {
        return new Elem(left, right, color);
    }
}
