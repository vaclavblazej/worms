package worms.ai;

import worms.model.Direction;
import worms.model.Model;
import worms.model.Worm;

/**
 * @author Václav Blažej
 */
public class AiRandomBrain extends AiBrain {
    @Override
    public void think(Worm worm, Model model) {
        final int i = random.nextInt();
        if (i > 0) {
            worm.setDirection(Direction.RIGHT);
        } else {
            worm.setDirection(Direction.LEFT);
        }
    }

    @Override
    public void reset() {
    }
}
