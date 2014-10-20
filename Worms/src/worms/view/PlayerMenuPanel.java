package worms.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import worms.Settings;
import worms.model.Model;

/**
 *
 * @author Patrik Faistaver
 * @author Václav Blažej
 * @author Štěpán Plachý
 */
public class PlayerMenuPanel extends JPanel {

    private final JComboBox<Integer> choosePlayerCount;
    private final ArrayList<PlayerSetupComponent> components;
    private final Model model;
    private final Settings settings;

    public PlayerMenuPanel(Model model, final Settings settings) {
        this.model = model;
        this.settings = settings;
        Integer maximumNumberOfPlayers = settings.getMaximumPlayerCount();
        setLayout(new GridLayout(maximumNumberOfPlayers / 2 + 1, 1));
        components = new ArrayList<>(maximumNumberOfPlayers);
        Integer[] txt = new Integer[maximumNumberOfPlayers - 1];
        for (int i = 0; i < maximumNumberOfPlayers - 1; i++) {
            txt[i] = i + 2;
        }
        choosePlayerCount = new JComboBox<>(txt);
        add(choosePlayerCount);
        choosePlayerCount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final Integer playerCount = (Integer) choosePlayerCount.getSelectedItem();
                settings.setPlayerCount(playerCount);
            }
        });
        choosePlayerCount.setSelectedIndex(0);
        for (int i = 0; i < maximumNumberOfPlayers; i++) {
            components.add(new PlayerSetupComponent(settings, i));
        }
        for (PlayerSetupComponent pl : components) {
            add(pl);
        }
    }
}
