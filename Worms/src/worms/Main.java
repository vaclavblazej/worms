package worms;

import java.util.LinkedList;
import javax.swing.SwingUtilities;

/**
 *
 * @author Pajcak & Venca
 */
public class Main {

    private static MainWindow mainWindow;
    private static GameWindow gameWindow;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initMenu();
            }
        });
    }

    public static void initMenu() {
        mainWindow = new MainWindow();
    }

    public static void initGame(LinkedList <Elem> controls) {
        gameWindow = new GameWindow(controls);
    }
}
