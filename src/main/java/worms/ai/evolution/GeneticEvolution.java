package worms.ai.evolution;

import worms.Common;
import worms.Settings;
import worms.ai.AiNeuralBrain;
import worms.ai.ComputerPlayer;
import worms.controller.Controller;
import worms.model.Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Václav Blažej
 */
public class GeneticEvolution extends EvolutionStrategy {

    private SelectionStrategy strategy;
    private SelectionStrategy elitism;
    private Settings settings;
    private int epoch;
    private boolean running;

    public GeneticEvolution(Controller controller, Model model, Settings settings) {
        super(controller, model);
        this.settings = settings;
        this.strategy = new RouletteStrategy();
        this.elitism = new EliteStrategy();
        this.running = false;
        this.epoch = 0;
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
        for (int i = 0; i < settings.getPopulationSize(); i++) {
            population.add(new ComputerPlayer("test", Common.randomColor(), new AiNeuralBrain()));
        }
        while (!stopCondition()) {
            epoch++;
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            List<Individual> parents = selectParents();
            List<Individual> children = new ArrayList<>();
            for (int i = 0; i < settings.getChildrenSize(); i++) {
                if (Common.getRandomBoolean(0.1)) {
                    Individual a = parents.get(Common.random.nextInt(settings.getPopulationSize()));
                    Individual b = parents.get(Common.random.nextInt(settings.getPopulationSize()));
                    Individual ind = a.cross(b).mutate();
                    children.add(ind);
                }
            }
            children.addAll(population); // compete with parents
//            List<Double> childrenProbabilities = children.stream().map(this::evaluate).collect(Collectors.toList());
            List<Double> childrenProbabilities = new ArrayList<>();
            double c = children.get(children.size() - 1).fitness() / children.size();
            for (Individual child : children) {
                this.evaluate(child);
                childrenProbabilities.add(c + child.fitness());
                c++;
            }
            children.sort(Comparator.comparingInt(Individual::fitness));
            population.sort(Comparator.comparingInt(Individual::fitness));
            System.out.print("children: ");
            for (Individual child : children) {
                System.out.print(child.fitness() + " ");
            }
            System.out.println();
            List<Individual> newGeneration = new ArrayList<>();
            newGeneration.add(elitism.select(children, childrenProbabilities));
            while (newGeneration.size() < settings.getPopulationSize()) {
                newGeneration.add(strategy.select(children, childrenProbabilities));
            }
            System.out.print("population: ");
            for (Individual child : population) {
                System.out.print(child.fitness() + " ");
            }
            System.out.println();
            setPopulation(newGeneration);
        }

    }

    private List<Individual> selectParents() {
        return population;
    }

    private boolean stopCondition() {
        return !running;
    }

}
