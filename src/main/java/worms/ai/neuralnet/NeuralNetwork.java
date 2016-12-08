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
        size = 1 + input + middle + output;
        matrix = Matrix.getRandomMatrix(size, size);
        List<Vector> values = this.matrix.getMatrix();
        for (int i = 0; i < size; i++) {
            values.get(0).setValue(i, i == 0 ? 1 : 0);
        }
//        System.out.println(this.matrix);
        state = new Vector(size);
        state.setValue(0, 1);
    }

    public void resetState() {
        state = new Vector(size);
        state.setValue(0, 1);
        state.setSubvector(1 + input, 1 + input + middle, Vector.getRandomVector(middle));
    }

    public Vector tick(Vector inputList) {
        // prepare inputs
        if (inputList.size() != input) {
            throw new RuntimeException("inputlist has bad length " + inputList.size() + " != " + input);
        }
        state.setValue(0, 1);
        state.setSubvector(1, 1 + input, inputList);
        // compute difference
        state = matrix.cross(state);
        // extract output
        return state.getSubvector(size - output, size);
    }

    public NeuralNetwork mutate() {
        NeuralNetwork result = new NeuralNetwork(this);
        List<Vector> matrix = result.matrix.getMatrix();
        int h = random.nextInt(matrix.size() - 1);
        Vector vector = matrix.get(h + 1);
        int w = random.nextInt(vector.size());
        double oldval = vector.get(w);
        double jump;
        if (random.nextInt() % 1000 < 4) {
            jump = random.nextDouble() - 0.5;
        } else {
            jump = 0.5 + (random.nextDouble() - 0.5) / 1000;
        }
        vector.setValue(w, Math.min(1, Math.max(0, oldval + jump)));
        return result;
    }

    public Vector getState() {
        return state;
    }
}
