package worms.ai.evolution;

import worms.controller.Controller;
import worms.model.Model;
import worms.model.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Václav Blažej
 */
public abstract class EvolutionStrategy {

    protected final Controller controller;
    protected final Model model;
    protected final List<Individual> population;

    public EvolutionStrategy(Controller controller, Model model) {
        this.controller = controller;
        this.model = model;
        this.population = Collections.synchronizedList(new ArrayList<>());
    }

    public abstract void runIteration();

    public void sortPopulation() {
        population.sort(Comparator.comparingDouble(Individual::fitness));
    }

    public double evaluate(Individual individual) {
        if (!(individual instanceof Player)) throw new RuntimeException("Individual is not a player");
        controller.evaluate((Player) individual);
        return individual.fitness();
    }

    public List<Individual> getPopulation() {
        return population;
    }

    public void setPopulation(List<Individual> newPopulation) {
        population.clear();
        population.addAll(newPopulation);
    }

    public void evaluateBest() {
        sortPopulation();
        Individual individual = population.get(population.size() - 1);
        new Thread(() -> controller.evaluate((Player) individual)).start();
    }
}
