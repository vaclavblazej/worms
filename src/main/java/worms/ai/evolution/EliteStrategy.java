package worms.ai.evolution;

import java.util.List;

/**
 * @author Václav Blažej
 */
public class EliteStrategy implements SelectionStrategy {

    @Override
    public Individual select(List<Individual> population, List<Double> probability) {
        return population.get(population.size() - 1);
    }
}
