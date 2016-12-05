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
        for (Vector vector : copy.matrix) {
            matrix.add(new Vector(vector));
        }
    }

    public Matrix(int height, int width) {
        this.height = height;
        this.width = width;
        matrix = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            matrix.add(i, new Vector(width));
        }
    }

    public Vector cross(Vector vector) {
        Vector result = new Vector(matrix.size());
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result.setValue(i, matrix.get(i).get(j) * vector.get(i));
            }
        }
        return result;
    }

    public List<Vector> getMatrix() {
        return matrix;
    }

    public void setMatrix(List<Vector> matrix) {
        this.matrix = matrix;
    }
}
