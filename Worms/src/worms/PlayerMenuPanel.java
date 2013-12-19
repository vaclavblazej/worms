package worms;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 *
 * @author Skarab
 */
public class PlayerMenuPanel extends JPanel {

    LinkedList<PlayerSetupComponent> components;

    public PlayerMenuPanel(int numberOfPlayers) {
        setLayout(new GridLayout(numberOfPlayers, 1));
        components = new LinkedList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            PlayerSetupComponent c = new PlayerSetupComponent();
            add(c);
            components.add(c);
        }
    }

    public LinkedList<Elem> getControls() {
        LinkedList<Elem> controls = new LinkedList<>();
        /*for (PlayerSetupComponent tmp : components) {
            controls.add(tmp.getPlayerControls());
        }*/
        // TODO - create controls from set variables, they will propagate properly into game window
        controls.add(new Elem(65, 68, Color.RED));
        controls.add(new Elem(37, 39, Color.GREEN));

        return controls;
    }
}
