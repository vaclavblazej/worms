package worms.model;

import java.awt.*;

/**
 *
 * @author Patrik Faistaver
 * @author Václav Blažej
 * @author Štěpán Plachý
 */
public class HumanPlayer extends Player{


    public HumanPlayer(int id, String name, Color color) {
        super(id, name,color);
    }

    @Override
    public void prepare(Model model) {
    }
}
