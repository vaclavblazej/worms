package worms.ai.neuralnet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Václav Blažej
 */
public class Vector {

    private List<Double> vector;

    public Vector(Vector copy) {
        List<Double> vector = copy.getVector();
        for (Double n : vector) {
            vector.add(n);
        }
    }

    public Vector(int size) {
        Random random = new Random();
        vector = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            vector.add(random.nextDouble());
        }
    }

    public double get(int index) {
        return vector.get(index);
    }

    public void setValue(int index, double value) {
        vector.set(index, value);
    }

    public List<Double> getVector() {
        return vector;
    }

    public void setVector(List<Double> vector) {
        this.vector = vector;
    }

    public void setSubvector(int from, int to, Vector vector) {
        for (int i = from, j = 0; i < to; i++, j++) {
            vector.setValue(i, vector.get(j));
        }
    }

    public Vector getSubvector(int from, int to) {
        Vector result = new Vector(to - from);
        for (int i = from, j = 0; i < to; i++, j++) {
            result.setValue(j, this.get(i));
        }
        return result;
    }

    public int size(){
        return vector.size();
    }
}
