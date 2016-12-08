package worms.ai.neuralnet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Václav Blažej
 */
public class Matrix {

    private int height, width;
    private List<Vector> matrix;

    public Matrix(Matrix copy) {
        this.height = copy.height;
        this.width = copy.width;
        this.matrix = new ArrayList<>();
        for (Vector vector : copy.matrix) {
            matrix.add(new Vector(vector));
        }
    }

    public Matrix(int height, int width) {
        this.height = height;
        this.width = width;
        this.matrix = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            this.matrix.add(i, new Vector(width));
        }
    }

    public static Matrix getRandomMatrix(int height, int width) {
        Matrix result = new Matrix(height, width);
        result.height = height;
        result.width = width;
        result.matrix = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            result.matrix.add(i, Vector.getRandomVector(width));
        }
        return result;
    }

    public Vector cross(Vector vector) {
        assert vector.size() == width;
        Vector result = new Vector(matrix.size());
        for (int i = 0; i < height; i++) {
            double value = 0;
            for (int j = 0; j < width; j++) {
                double toAdd = matrix.get(i).get(j) * vector.get(j);
                value += toAdd > 0 ? toAdd : (j == 0 ? toAdd : 0);
            }
            result.setValue(i, value);
        }
        return result;
    }

    public List<Vector> getMatrix() {
        return matrix;
    }

    public void setMatrix(List<Vector> matrix) {
        this.matrix = matrix;
    }

    @Override
    public String toString() {
        String res = "Matrix{";
        for (Vector vector : matrix) {
            res += vector.toString() + '\n';
        }
        res += '}';
        return res;
    }
}
