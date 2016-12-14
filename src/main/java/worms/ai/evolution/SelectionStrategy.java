package worms.ai.evolution;

import java.util.List;

/**
 * @author Václav Blažej
 */
public interface SelectionStrategy {

    Individual select(List<Individual> population, List<Double> probability);
}
