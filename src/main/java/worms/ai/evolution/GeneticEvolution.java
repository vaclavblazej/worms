package worms.ai.evolution;

import worms.Common;
import worms.Settings;
import worms.ai.AiNeuralBrain;
import worms.ai.ComputerPlayer;
import worms.controller.Controller;
import worms.model.Model;

import java.awt.*;
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
        for (int i = 0; i < settings.getPopulationSize(); i++) {
            final ComputerPlayer individual = new ComputerPlayer("initial", Common.randomColor(), new AiNeuralBrain());
            this.evaluate(individual);
            population.add(individual);
        }
        while (!stopCondition()) {
            List<Individual> parents = selectParents();
            List<Individual> children = new ArrayList<>();
            for (int i = 0; i < settings.getChildrenSize() / 20; i++) {
                children.add(new ComputerPlayer("new", Color.red, new AiNeuralBrain()));
            }
            for (int i = children.size(); i < settings.getChildrenSize(); i++) {
                if (Common.getRandomBoolean(0.9)) { // cross
                    Individual a = parents.get(Common.random.nextInt(settings.getPopulationSize()));
                    Individual b = parents.get(Common.random.nextInt(settings.getPopulationSize()));
                    Individual ind = a.cross(b);
                    children.add(ind);
                } else { // mutation
                    Individual a = parents.get(Common.random.nextInt(settings.getPopulationSize()));
                    Individual ind = a.mutate();
                    children.add(ind);
                }
            }
//            List<Double> childrenProbabilities = children.stream().map(this::evaluate).collect(Collectors.toList());
            List<Double> childrenProbabilities = new ArrayList<>();

            for (Individual child : children) {
                this.evaluate(child);
            }
            children.addAll(population); // compete with parents
            final int fitBest = population.get(population.size() - 1).fitness() / population.size();
            children.sort(Comparator.comparingInt(Individual::fitness));
            double c = 1;
            for (Individual child : children) {
                childrenProbabilities.add(c * fitBest + child.fitness());
                c++;
            }

            List<Individual> newGeneration = new ArrayList<>();
            // classical roulette
            for (int i = 0; i < settings.getPopulationSize() / 2; i++) {
                final Individual select = strategy.select(children, childrenProbabilities);
                newGeneration.add(select);
            }
            // choose elite which is not similar to other individuals
            for (int i = 0; i < settings.getPopulationSize() / 4; i++) {
                final Individual select = elitism.select(children, childrenProbabilities);
                final int q = children.indexOf(select);
                double mn = 1000000;
                for (Individual individual : newGeneration) {
                    if (individual != select && individual.fitness() == select.fitness()) {
                        mn = Math.min(mn, select.distance(individual));
                    }
                }
                if (mn > 0.001) {
                    newGeneration.add(select);
                }
                children.remove(q);
                childrenProbabilities.remove(q);
            }
            while (newGeneration.size() < settings.getPopulationSize()) {
                final ComputerPlayer individual = new ComputerPlayer("new", new Color(44, 195, 45), new AiNeuralBrain());
                evaluate(individual);
                newGeneration.add(individual);
            }

            setPopulation(newGeneration);
            printPopulation();
            epoch++;
        }
    }

    private void printPopulation() {
        sortPopulation();
        System.out.println(epoch + " "
                + population.get(population.size() - 1).fitness() + " "
                + population.get(population.size() / 2).fitness() + " "
                + population.get(0).fitness());
    }

    private List<Individual> selectParents() {
        return population;
    }

    private boolean stopCondition() {
        return !running;
    }

    public int getEpoch() {
        return epoch;
    }
}
