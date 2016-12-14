package worms.ai.evolution;

import worms.ai.ComputerPlayer;
import worms.controller.Controller;
import worms.model.Model;
import worms.model.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Václav Blažej
 */
public class GeneticEvolution extends EvolutionStrategy {

    private SelectionStrategy strategy;
    private boolean running;

    public GeneticEvolution(Controller controller, Model model) {
        super(controller, model);
        this.strategy = new RouletteStrategy();
        this.running = false;
    }

    public void setRunning(boolean run) {
        this.running = run;
        if (run) {
            Thread thread = new Thread(this::runIteration);
            thread.start();
        }
    }

    @Override
    public void runIteration() {
//        for (Individual individual : population) {
//            evaluate(individual);
//        }
//        sortPopulation();
//        Individual best = population.get(population.size() - 1);
        ArrayList<Player> players = model.getPlayers();
        for (Player player : players) {
            if (player instanceof ComputerPlayer) {
                population.add((ComputerPlayer) player);
            }
        }
        while (!stopCondition()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<Individual> parents = selectParents();
            List<Individual> children = new ArrayList<>();
            for (int i = 0; i < parents.size(); i++) {
                for (int j = i + 1; j < parents.size(); j++) {
                    Individual a = parents.get(i);
                    Individual b = parents.get(j);
                    Individual ind = a.cross(b).mutate();
                    children.add(ind);
                }
            }
            children.addAll(population); // compete with parents
//            List<Double> childrenProbabilities = children.stream().map(this::evaluate).collect(Collectors.toList());
            List<Double> childrenProbabilities = new ArrayList<>();
            double c = 1;
            for (Individual child : children) {
                this.evaluate(child);
                childrenProbabilities.add(c++);
            }
            children.sort(Comparator.comparingInt(Individual::fitness));
            population.sort(Comparator.comparingInt(Individual::fitness));
            System.out.print("children: ");
            for (Individual child : children) {
                System.out.print(child.fitness() + " ");
            }
            System.out.println();
            List<Individual> newGeneration = new ArrayList<>();
            while (newGeneration.size() < population.size()) {
                newGeneration.add(strategy.select(children, childrenProbabilities));
            }
//            if(newGeneration.get(newGeneration.size()-1).fitness() < population.get(population.size()-1).fitness()){
//                newGeneration.set(newGeneration.size() - 1, population.get(population.size() - 1));
//            }
            System.out.print("population: ");
            for (Individual child : population) {
                System.out.print(child.fitness() + " ");
            }
            System.out.println();
            population.clear();
            population.addAll(newGeneration);
        }

    }

    private List<Individual> selectParents() {
        return population;
    }

    private boolean stopCondition() {
        return !running;
    }
}
