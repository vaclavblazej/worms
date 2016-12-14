package worms.view;

import sun.swing.DefaultLookup;
import worms.Settings;
import worms.ai.evolution.EvolutionWrapper;
import worms.ai.evolution.GeneticEvolution;
import worms.controller.Controller;
import worms.model.Model;
import worms.model.Player;
import worms.view.component.CustomCheckbox;
import worms.view.component.CustomSlider;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

/**
 * @author Václav Blažej
 */
public class EvolutionView extends JPanel implements ActionListener {

    private final JLabel turn = new JLabel();
    private final Timer refreshGraphics = new Timer(200, this);
    private int iterations = 0;
    private Controller controller;
    private Model model;
    private Settings settings;
    private GeneticEvolution geneticEvolution;

    public EvolutionView(Model model, Controller controller, Settings settings, EvolutionWrapper evolution) {
        this.model = model;
        this.controller = controller;
        this.settings = settings;
        this.geneticEvolution = evolution.getEvolutionStrategy();
        this.setPreferredSize(new Dimension(400, 600));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(turn);
        this.add(createPlayerList());
        this.add(graphicsControls());
        this.add(createSimulationControls());
        JTabbedPane tabs = new JTabbedPane();
        tabs.setPreferredSize(new Dimension(220, 200));
        tabs.addTab("Genetic", createGeneticTab());
        tabs.addTab("Differential", createDifferentialTab());
        this.add(tabs);
        refreshGraphics.start();
    }

    private JComponent graphicsControls() {
        JPanel res = new JPanel();
        res.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
        res.add(new JLabel("Graphics settings"));
        final CustomCheckbox showRays = new CustomCheckbox("on", "off", false);
        showRays.addItemListener(e -> {
//            model.(e.getStateChange() == ItemEvent.SELECTED);
        });
        res.add(showRays);
        final CustomCheckbox skipGraphics = new CustomCheckbox("on", "off", true);
        skipGraphics.addItemListener(e -> {
            controller.setGRAPHICS(e.getStateChange() == ItemEvent.SELECTED);
        });
        res.add(skipGraphics);
        res.setBorder(new BevelBorder(BevelBorder.LOWERED));
        return res;
    }

    private JComponent createSimulationControls() {
        JPanel res = new JPanel();
        res.setLayout(new BoxLayout(res, BoxLayout.Y_AXIS));
        res.add(new JLabel("Simulation settings"));
        Integer[] list = {1, 2, 3, 4, 5};
        JComboBox<Integer> choosePlayerCount = new JComboBox<>(list);
        res.add(choosePlayerCount);
        choosePlayerCount.addActionListener(e -> {
            final Integer playerCount = (Integer) choosePlayerCount.getSelectedItem();
            settings.setPlayerCount(playerCount);
        });
        final JSlider speedSlider = new CustomSlider("Speed", 2, 100, 15);
        speedSlider.addChangeListener(evt -> {
            controller.setSimulationDelay(speedSlider.getValue());
        });
        res.add(speedSlider);
        final CustomCheckbox runButton = new CustomCheckbox("run", "stop", false);
        runButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                controller.startSimulationInThread();
            } else {
                controller.endSimulation();
            }
        });
        res.add(runButton);
//        final JSlider iterationSlider = new JSlider(1, 10000, 500);
//        iterationSlider.addChangeListener(evt -> {
//            iterations = iterationSlider.getValue();
//            runButton.setText("Start " + iterations + " iterations");
//        });
//        ChangeEvent ce = new ChangeEvent(iterationSlider);
//        for (ChangeListener cl : iterationSlider.getChangeListeners()) {
//            cl.stateChanged(ce);
//        }
//        res.add(iterationSlider);
        res.setBorder(new BevelBorder(BevelBorder.LOWERED));
        res.setPreferredSize(new Dimension(220, 200));
        return res;
    }

    private JComponent createGeneticTab() {
        JPanel res = new JPanel(new BorderLayout());
        res.setBorder(new BevelBorder(BevelBorder.LOWERED));
        final CustomCheckbox runButton = new CustomCheckbox("run", "stop", false);
        runButton.addItemListener(e -> {
            geneticEvolution.setRunning(e.getStateChange() == ItemEvent.SELECTED);
        });
        res.add(runButton);
        return res;
    }

    private JComponent createDifferentialTab() {
        JPanel res = new JPanel(new BorderLayout());
        res.setBorder(new BevelBorder(BevelBorder.LOWERED));
        return res;
    }

    private JComponent createPlayerList() {
        JList<Player> players = new JList<>();
        players.setModel(new AbstractListModel<Player>() {
            private ArrayList<Player> playerList = model.getPlayers();

            @Override
            public int getSize() {
                return playerList.size();
            }

            @Override
            public Player getElementAt(int index) {
                return playerList.get(index);
            }
        });
        players.setCellRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {
                Color color = model.getPlayers().get(index).getColor();
                setComponentOrientation(list.getComponentOrientation());

                Color bg = null;
                Color fg = null;

                JList.DropLocation dropLocation = list.getDropLocation();
                if (dropLocation != null
                        && !dropLocation.isInsert()
                        && dropLocation.getIndex() == index) {

                    bg = DefaultLookup.getColor(this, ui, "List.dropCellBackground");
                    fg = DefaultLookup.getColor(this, ui, "List.dropCellForeground");
                    isSelected = true;
                }
                fg = color;

                if (isSelected) {
                    setBackground(bg == null ? list.getSelectionBackground() : bg);
                    setForeground(fg == null ? list.getSelectionForeground() : fg);
                } else {
                    setBackground(list.getBackground());
                    setForeground(fg == null ? list.getSelectionForeground() : fg);
                }

                if (value instanceof Icon) {
                    setIcon((Icon) value);
                    setText("");
                } else {
                    setIcon(null);
                    setText((value == null) ? "" : value.toString());
                }

                setEnabled(list.isEnabled());
                setFont(list.getFont());

                Border border = null;
                if (cellHasFocus) {
                    if (isSelected) {
                        border = DefaultLookup.getBorder(this, ui, "List.focusSelectedCellHighlightBorder");
                    }
                    if (border == null) {
                        border = DefaultLookup.getBorder(this, ui, "List.focusCellHighlightBorder");
                    }
                } else {
                    border = new EmptyBorder(1, 1, 1, 1);
                }
                setBorder(border);

                return this;
            }
        });
        JScrollPane scroll = new JScrollPane(players);
        scroll.setPreferredSize(new Dimension(280, 120));
        scroll.setBorder(new BevelBorder(BevelBorder.LOWERED));
        return scroll;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        turn.setText("" + controller.getTurn());
    }
}
