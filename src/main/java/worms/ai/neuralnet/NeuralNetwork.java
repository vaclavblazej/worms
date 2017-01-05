package worms.ai.neuralnet;

import worms.Common;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

/**
 * @author Václav Blažej
 */
public class NeuralNetwork implements Serializable {

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

    public NeuralNetwork(int input, int hidden, int output) {
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

    public NeuralNetwork() {
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
            hiddenLayer.set(i, Common.sigmoid(hiddenLayer.get(i)));
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
        double type = Common.random.nextDouble();
        int num = Common.getRandomBoolean(0.08) ? 100 : 1;
        for (int i = 0; i < num; i++) {
            List<Vector> matrix = result.matrix.getMatrix();
            int h = random.nextInt(matrix.size() - 1);
            Vector vector = matrix.get(h + 1);
            int w = random.nextInt(vector.size());
            double change;
            if (type < 0.04) {
                change = (random.nextDouble() - 0.5 / 10);
            } else {
                change = (random.nextDouble() - 0.5) / 1000;
            }
            double oldval = vector.get(w);
            vector.set(w, oldval + change);
        }
        return result;
    }

    public Vector getInVector() {
        return inVector;
    }


    public Matrix getMatrix() {
        return matrix;
    }
}
