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

    public void initializePopulation() {
        population.clear();
        for (int i = 0; i < settings.getPopulationSize(); i++) {
            final ComputerPlayer individual = new ComputerPlayer("initial", Common.randomColor(), new AiNeuralBrain());
            this.evaluate(individual);
            population.add(individual);
        }
    }

    @Override
    public void runIteration() {
        initializePopulation();
        while (!stopCondition()) {
            List<Individual> parents = population;
            List<Individual> children = new ArrayList<>();
            for (int i = children.size(); i < settings.getChildrenSize(); i++) {
                if (Common.getRandomBoolean(0.4)) { // cross
                    Individual a = parents.get(Common.random.nextInt(parents.size()));
                    Individual b = parents.get(Common.random.nextInt(parents.size()));
                    Individual ind = a.cross(b);
                    children.add(ind);
                } else { // mutation
                    Individual a = parents.get(Common.random.nextInt(parents.size()));
                    Individual ind = a.mutate();
                    children.add(ind);
                }
            }
            for (Individual child : children) {
                this.evaluate(child);
            }
            children.addAll(population); // compete with parents
            children.sort(Comparator.comparingInt(Individual::fitness));
            double fitBest = children.get(children.size() - 1).fitness() / children.size();
            List<Double> childrenProbabilities = new ArrayList<>();
            for (Individual child : children) {
                childrenProbabilities.add(fitBest + child.fitness());
            }
            List<Individual> newGeneration = new ArrayList<>();
            newGeneration.add(elitism.select(children, childrenProbabilities));
            while (newGeneration.size() < settings.getPopulationSize()) {
                newGeneration.add(strategy.select(children, childrenProbabilities));
            }
            setPopulation(newGeneration);
            sortPopulation();
            printPopulation();
            epoch++;
        }
    }

    private void printPopulation() {
        System.out.println(epoch + " "
                + population.get(population.size() - 1).fitness() + " "
                + population.get(population.size() / 2).fitness() + " "
                + population.get(0).fitness());
    }

    private boolean stopCondition() {
        return !running;
    }

    public int getEpoch() {
        return epoch;
    }
}
