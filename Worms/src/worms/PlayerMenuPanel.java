package worms;

import java.awt.Color;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 *
 * @author Skarab
 */
public class PlayerMenuPanel extends JPanel {

    public PlayerMenuPanel(int numberOfPlayers) {
        for (int i = 0; i < numberOfPlayers; i++) {
            // TODO - gui to set number of active players and set their colors and controls
        }
    }

    public LinkedList<Elem> getControls() {
        LinkedList<Elem> controls = new LinkedList<>();
        
        // TODO - create controls from set variables, they will propagate properly into game window
        controls.add(new Elem(65, 68, Color.RED));
        controls.add(new Elem(37, 39, Color.GREEN));
        
        return controls;
    }
}
