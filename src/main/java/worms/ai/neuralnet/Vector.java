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
        this.vector = new ArrayList<>();
        for (Double n : copy.getVector()) {
            this.vector.add(n);
        }
    }

    public Vector(int size) {
        vector = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            vector.add(0.0);
        }
    }

    public static Vector getRandomVector(int size) {
        Random random = new Random();
        Vector result = new Vector(size);
        for (int i = 0; i < size; i++) {
            result.setValue(i, random.nextDouble() / size / size);
        }
        return result;
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
        assert to - from == vector.size();
        for (int i = from, j = 0; i < to; i++, j++) {
            this.setValue(i, vector.get(j));
        }
    }

    public Vector getSubvector(int from, int to) {
        Vector result = new Vector(to - from);
        for (int i = from, j = 0; i < to; i++, j++) {
            result.setValue(j, this.get(i));
        }
        return result;
    }

    public int size() {
        return vector.size();
    }

    @Override
    public String toString() {
        String res = "";
        for (Double aDouble : vector) {
            res += String.format("%.3f ", aDouble);
        }
        return res;
    }
}
