package worms.ai.neuralnet;

import java.util.List;
import java.util.Random;

/**
 * @author Václav Blažej
 */
public class NeuralNetwork {

    private static final Random random = new Random();
    private int input, hidden, output, inSize, outSize;
    private Matrix matrix;
    private Vector inVector, outVector;

    public NeuralNetwork(NeuralNetwork copy) {
        this.input = copy.input;
        this.hidden = copy.hidden;
        this.output = copy.output;
        this.inSize = copy.inSize;
        this.outSize = copy.outSize;
        this.matrix = new Matrix(copy.matrix);
        this.inVector = new Vector(copy.inVector);
        this.outVector = new Vector(copy.outVector);
    }

    public NeuralNetwork() {
    }

    public void prepare(int input, int hidden, int output) {
        this.input = input;
        this.hidden = hidden;
        this.output = output;
        inSize = 1 + input + hidden;
        outSize = output + hidden;
        matrix = Matrix.getRandomMatrix(outSize, inSize);
        inVector = new Vector(inSize);
        inVector.set(0, -1.0 * inSize);
        outVector = new Vector(outSize);
    }

    public void resetState() {
        // todo
//        inVector = new Vector(inSize);
//        inVector.setSubvector(1 + input, 1 + input + hidden, Vector.getRandomVector(hidden));
    }

    public Vector tick(Vector inputList) {
        // prepare inputs
        if (inputList.size() != input) {
            throw new RuntimeException("inputlist has bad length " + inputList.size() + " != " + input);
        }
        inVector.setSubvector(1, 1 + input, inputList);
        // compute difference
        outVector = matrix.cross(inVector);
        // copy hidden layer
        Vector hiddenLayer = outVector.getSubvector(output, outSize);
        for (int i = 0; i < hiddenLayer.size(); i++) {
            hiddenLayer.set(i, sigmoid(hiddenLayer.get(i)));
        }
        inVector.setSubvector(1 + input, inSize, hiddenLayer);
//         extract output
//        System.out.println("inputs:" + inVector);
        Vector subvector = outVector.getSubvector(0, output);
//        System.out.println("response: " + subvector);
        return subvector;
    }

    public NeuralNetwork mutate() {
        NeuralNetwork result = new NeuralNetwork(this);
        if (random.nextInt() % 1000 < 10) {
            result.matrix = Matrix.getRandomMatrix(outSize, inSize);
        } else {
            List<Vector> matrix = result.matrix.getMatrix();
            int h = random.nextInt(matrix.size() - 1);
            Vector vector = matrix.get(h + 1);
            int w = random.nextInt(vector.size());
            double oldval = vector.get(w);
            double jump;
            if (random.nextInt() % 100 < 4) {
                jump = random.nextDouble() - 0.5;
            } else {
                jump = (random.nextDouble() - 0.5) / 1000;
            }
            vector.set(w, Math.min(1, Math.max(0, oldval + jump)));
        }
        return result;
    }

    public Vector getInVector() {
        return inVector;
    }

    private double sigmoid(double value) {
        return 1 / (1 + Math.exp(-value));
    }

    public Matrix getMatrix() {
        return matrix;
    }
}
