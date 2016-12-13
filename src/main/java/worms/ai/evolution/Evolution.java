package worms.ai.evolution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Václav Blažej
 */
public abstract class Evolution {

    protected List<Individual> population;

    public Evolution() {
        population=new ArrayList<>();
    }

    public abstract void runIteration();

    public void sortPopulation() {
        population.sort(Comparator.comparingDouble(Individual::fitness));
    }

    public void evaluate(Individual individual){
        // todo connect to model and controller
    }
}
