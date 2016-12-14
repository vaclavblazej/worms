package worms.ai.evolution;

import worms.controller.Controller;
import worms.model.Model;

/**
 * @author Václav Blažej
 */
public class EvolutionWrapper {

    private GeneticEvolution evolutionStrategy;

    public EvolutionWrapper(Controller controller, Model model) {
        this.evolutionStrategy = new GeneticEvolution(controller, model);
//        evolutionStrategy.setAsMainEvolutionMethod();
    }

    public GeneticEvolution getEvolutionStrategy() {
        return evolutionStrategy;
    }

    public void setEvolutionStrategy(GeneticEvolution evolutionStrategy) {
        this.evolutionStrategy = evolutionStrategy;
    }
}
