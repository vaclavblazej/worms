package worms.ai.neuralnet;

import worms.Common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Václav Blažej
 */
public class Vector extends ArrayList<Double> implements Serializable {

    public Vector(Vector copy) {
        this.clear();
        for (Double n : copy) {
            this.add(n);
        }
    }

    public Vector(int size) {
        for (int i = 0; i < size; i++) {
            this.add(0.0);
        }
    }

    public static Vector getRandomVector(int size) {
        Random random = new Random();
        Vector result = new Vector(size);
        for (int i = 0; i < size; i++) {
            if(Common.getRandomBoolean(0.2)) {
                result.set(i, random.nextDouble() * random.nextInt(16) * (random.nextBoolean() ? 1 : -1));
            }else{
                result.set(i, 0.0);
            }
        }
        return result;
    }

    public void setSubvector(int from, int to, Vector vector) {
        assert to - from == vector.size();
        for (int i = from, j = 0; i < to; i++, j++) {
            this.set(i, vector.get(j));
        }
    }

    public Vector getSubvector(int from, int to) {
        Vector result = new Vector(to - from);
        for (int i = from, j = 0; i < to; i++, j++) {
            result.set(j, this.get(i));
        }
        return result;
    }

    @Override
    public String toString() {
        String res = "";
        for (Double aDouble : this) {
            res += String.format("%.3f ", aDouble);
        }
        return res;
    }
}
