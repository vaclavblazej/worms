package worms.ai.evolution;

import worms.Settings;
import worms.controller.Controller;
import worms.model.Model;

/**
 * @author Václav Blažej
 */
public class EvolutionWrapper {

    private GeneticEvolution evolutionStrategy;

    public EvolutionWrapper(Controller controller, Model model, Settings settings) {
        this.evolutionStrategy = new GeneticEvolution(controller, model, settings);
//        evolutionStrategy.setAsMainEvolutionMethod();
    }

    public GeneticEvolution getEvolutionStrategy() {
        return evolutionStrategy;
    }

    public void setEvolutionStrategy(GeneticEvolution evolutionStrategy) {
        this.evolutionStrategy = evolutionStrategy;
    }
}
