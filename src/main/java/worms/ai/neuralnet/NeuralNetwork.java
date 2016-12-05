package worms.ai.neuralnet;

import java.util.List;
import java.util.Random;

/**
 * @author Václav Blažej
 */
public class NeuralNetwork {

    private static final Random random = new Random();
    private int input, middle, output, size;
    private Matrix matrix;
    private Vector state;

    public NeuralNetwork(NeuralNetwork copy) {
        this.input = copy.input;
        this.middle = copy.middle;
        this.output = copy.output;
        this.size = copy.size;
        this.matrix = new Matrix(copy.matrix);
        this.state = new Vector(copy.state);
    }

    public NeuralNetwork() {
    }

    public void prepare(int input, int middle, int output) {
        this.input = input;
        this.middle = middle;
        this.output = output;
        size = input + middle + output;
        matrix = new Matrix(size, size);
        state = new Vector(size);
    }

    public Vector tick(Vector inputList) {
        // prepare inputs
        if (inputList.getVector().size() != input)
            throw new RuntimeException("inputlist has bad length " + inputList.getVector().size() + " != " + input);
        state.setSubvector(0, input, inputList);
        // compute difference
        state = matrix.cross(state);
        // prepare output
        return state.getSubvector(size - output, size);
    }

    public NeuralNetwork mutate() {
        NeuralNetwork result = new NeuralNetwork(this);
        List<Vector> matrix = result.matrix.getMatrix();
        int h = random.nextInt(matrix.size());
        Vector vector = matrix.get(h);
        int w = random.nextInt(vector.size());
        vector.setValue(w, random.nextDouble());
        return result;
    }
}
