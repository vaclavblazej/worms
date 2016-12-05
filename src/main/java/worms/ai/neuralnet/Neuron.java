package worms.ai.neuralnet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Václav Blažej
 */
public class Neuron {

    private final static Random random = new Random();
    List<Neuron> next;
    List<Previous> previous;
    private double value;

    public Neuron() {
        next = new ArrayList<>();
        previous = new ArrayList<>();
        value = 0;
    }

    public void addConnection(Neuron n) {
        next.add(n);
        n.addPrevious(this);
    }

    private void addPrevious(Neuron neuron) {
        previous.add(new Previous(neuron, random.nextDouble()));
    }

    public void compute() {
        double result = 0;
        for (Previous p : previous) {
            result += p.neuron.getValue() * p.weight;
        }
        value = (result > 0 ? 1 : 0);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Neuron{" +
                "previous=" + previous.hashCode() +
                ", value=" + value +
                '}';
    }

    private static class Previous {

        Neuron neuron;
        double weight;

        public Previous(Neuron neuron, double weight) {
            this.neuron = neuron;
            this.weight = weight;
        }
    }
}
