/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package worms;

import java.awt.Color;

/**
 *
 * @author Pajcak & Venca
 */
public class Elem {

    public Elem(int left, int right, Color color) {
        this.left = left;
        this.right = right;
        this.color = color;
    }
    public int left;
    public int right;
    public Color color;
}
