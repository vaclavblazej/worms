/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package worms;

import javax.swing.JFrame;

/**
 *
 * @author Pajcak & Venca
 */
public class Main {
    private static MainWindow mainWindow;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        mainWindow = new MainWindow();
        mainWindow.showMe();      
    }
}
