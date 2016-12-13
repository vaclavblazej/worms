package worms.ai.evolution;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Václav Blažej
 */
class GeneticEvolution extends Evolution{

    private SelectionStrategy strategy;

    @Override
    public void runIteration() {
        for (Individual individual : population) {
            evaluate(individual);
        }
        sortPopulation();
        Individual best = population.get(population.size() - 1);
        while(stopCondition()){
            List<Individual> parents = selectParents();
            List<Individual> children = new ArrayList<>();
            for (int i = 0; i < parents.size(); i++) {
                for (int j = i+1; j < parents.size(); j++) {
                    Individual a = parents.get(i);
                    Individual b = parents.get(j);
                    Individual ind = a.cross(b).mutate();
                    children.add(ind);
                }
            }
        }

    }

    private List<Individual> selectParents() {
        return null;
    }

    private boolean stopCondition() {
        return false;
    }
}
