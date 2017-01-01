package worms.ai.evolution;

import java.util.List;
import java.util.Random;

/**
 * @author Václav Blažej
 */
public class RouletteStrategy implements SelectionStrategy {

    private static final Random random = new Random();

    @Override
    public Individual select(List<Individual> population, List<Double> probability) {
        double sum = probability.stream().mapToDouble(Double::doubleValue).sum();
        double d = random.nextDouble() * 1000000000000L;
        d = d % sum;
        int idx = -1;
        while (d > 0) {
            d -= probability.get(++idx);
        }
        return population.get(idx);
    }
}
