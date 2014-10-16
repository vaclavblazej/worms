package worms.view;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import worms.Settings;
import worms.model.Model;

/**
 *
 * @author Skarab
 */
public class PlayerMenuPanel extends JPanel {

    JComboBox<Integer> choosePlayerCount;
    ArrayList<PlayerSetupComponent> components;

    public PlayerMenuPanel(Model model, Settings settings) {
        Integer numberOfPlayers = settings.getPlayerCount();
        setLayout(new GridLayout(numberOfPlayers / 2 + 1, 1));
        components = new ArrayList<>(numberOfPlayers);
        Integer[] txt = new Integer[numberOfPlayers - 1];
        for (int i = 0; i < numberOfPlayers - 1; i++) {
            txt[i] = i + 2;
        }
        choosePlayerCount = new JComboBox<>(txt);
        add(choosePlayerCount);
        for (int i = 0; i < numberOfPlayers; i++) {
            components.add(new PlayerSetupComponent(model, i));
        }
        for (PlayerSetupComponent pl : components) {
            add(pl);
        }
        //"Player 1: < >", 65, 68, Color.RED
        //components.add(new PlayerSetupComponent("Player 2: A D", 37, 39, Color.GREEN));
        //components.add(new PlayerSetupComponent("Player 3: 3 9", 99, 105, Color.CYAN));
        //components.add(new PlayerSetupComponent("Player 4: J L", 74, 76, Color.PINK));
        //components.add(new PlayerSetupComponent("Player 5: V B", 86, 66, Color.ORANGE));
    }
}
