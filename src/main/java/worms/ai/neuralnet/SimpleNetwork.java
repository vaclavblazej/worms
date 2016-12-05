package worms.ai.neuralnet;

import java.util.ArrayList;

/**
 * Simple multilevel forward neural network where each level has number of neurons given in 'sizes' parameter.
 *
 * @author Václav Blažej
 */
public class SimpleNetwork extends NeuralNetwork {

    public SimpleNetwork(long... sizes) {
        assert sizes.length >= 2;
        long sum = 0;
        for (long size : sizes) {
            sum += size;
        }
        final ArrayList<Neuron> neurons = new ArrayList<>();
        for (int i = 0; i < sum; i++) {
            neurons.add(new Neuron());
        }
        prepare(neurons, sizes[0], sizes[sizes.length - 1]);
        for (int i = 1; i < sizes.length; i++) {
            for (int j = 0; j < sizes[i - 1]; j++) {
                for (int k = 0; k < sizes[i]; k++) {
                    addConnection(j, k);
                }
            }
        }
    }
}
