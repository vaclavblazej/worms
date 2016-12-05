package worms.ai.neuralnet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Václav Blažej
 */
public class NeuralNetwork {

    List<Neuron> orderedNeurons;
    long input;
    long output;

    public void addConnection(int a, int b) {
        final Neuron aa = orderedNeurons.get(a);
        final Neuron bb = orderedNeurons.get(b);
        aa.addConnection(bb);
    }

    public void prepare(List<Neuron> neurons, long input, long output) {
        orderedNeurons = neurons;
        this.input = input;
        this.output = output;
//        Set<Neu> todo = new HashSet<>();
//        for (Neuron neuron : neurons) {
//            todo.add(new Neu(neuron));
//        }
//        Queue<Neuron> queue = new LinkedList<>();
//        final Iterator<Neu> iterator = todo.iterator();
//        while (iterator.hasNext()) {
//            Neu neu = iterator.next();
//            if (neu.ancestors == 0) {
//                queue.add(neu.neuron);
//                iterator.remove();
//            }
//        }
//        while(!queue.isEmpty()){
//            final Neuron poll = queue.poll();
//            poll.
//        }
    }

    public List<Double> tick(List<Double> inputList) {
        // prepare inputs
        if(inputList.size() != input)throw new RuntimeException("inputlist has bad length");
        for (int i = 0; i < inputList.size(); i++) {
            orderedNeurons.get(i).setValue(inputList.get(i));
        }
        // compute difference
        for (Neuron neuron : orderedNeurons) {
            neuron.compute();
        }
        // prepare output
        final List<Double> outputList = new ArrayList<>();
        for (int i = (int) (orderedNeurons.size() - output); i < orderedNeurons.size(); i++) {
            outputList.add(orderedNeurons.get(i).getValue());
        }
        return outputList;
    }

    @Override
    public String toString() {
        return "NeuralNetwork{" +
                "orderedNeurons=\n" + orderedNeurons + '\n' +
                '}';
    }
//    private static class Neu {
//        Neuron neuron;
//        long ancestors;
//        public Neu(Neuron neuron) {
//            this.neuron = neuron;
//            ancestors = neuron.previous.size();
//        }
//    }
}
