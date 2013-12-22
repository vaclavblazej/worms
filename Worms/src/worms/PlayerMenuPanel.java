package worms;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 *
 * @author Skarab
 */
public class PlayerMenuPanel extends JPanel {

    JComboBox<Integer> players;
    ArrayList<PlayerSetupComponent> components;

    public PlayerMenuPanel(int numberOfPlayers) {
        setLayout(new GridLayout(numberOfPlayers, 1));
        components = new ArrayList<>(numberOfPlayers);
        Integer[] txt = new Integer[numberOfPlayers - 1];
        for (int i = 0; i < numberOfPlayers - 1; i++) {
            txt[i] = i + 2;
        }
        players = new JComboBox<>(txt);
        add(players);
        components.add(new PlayerSetupComponent("Player 1: < >", 65, 68, Color.RED));
        components.add(new PlayerSetupComponent("Player 2: A D", 37, 39, Color.GREEN));
        components.add(new PlayerSetupComponent("Player 3: 3 9", 99, 105, Color.CYAN));
        components.add(new PlayerSetupComponent("Player 4: J L", 74, 76, Color.PINK));
        components.add(new PlayerSetupComponent("Player 5: V B", 86, 66, Color.ORANGE));
        for (PlayerSetupComponent pl : components) {
            add(pl);
        }
    }

    public LinkedList<Elem> getControls() {
        LinkedList<Elem> controls = new LinkedList<>();
        Integer i = players.getItemAt(players.getSelectedIndex());
        for (int j = 0; j < i; j++) {
            components.get(j).getPlayerControls();
            controls.add(components.get(j).getPlayerControls());
        }
        return controls;
    }
}
