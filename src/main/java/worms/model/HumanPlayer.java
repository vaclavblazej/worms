package worms.model;

import java.awt.*;

/**
 *
 * @author Patrik Faistaver
 * @author Václav Blažej
 * @author Štěpán Plachý
 */
public class HumanPlayer extends Player{


    public HumanPlayer(String name, Color color) {
        super(name,color);
    }

    @Override
    public void prepare(Model model) {
    }
}
