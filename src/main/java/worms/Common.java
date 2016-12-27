package worms;

import java.awt.*;
import java.io.*;
import java.util.Random;
import java.util.function.Supplier;

/**
 * @author Václav Blažej
 */
public final class Common {

    public static Random random = new Random();
    private static final Supplier<Float> func = () -> ((random.nextFloat() % 0.7f) + 0.3f) % 1;

    public static double sigmoid2(double value) {
        return 2 / (1 + Math.exp(-value)) - 1;
    }

    public static double sigmoid(double value) {
        return 1 / (1 + Math.exp(-value));
    }

    public static Color randomColor() {
        return Color.getHSBColor(func.get(), func.get(), func.get());
    }

    public static <T> void save(T list, String file) {
        try {
            FileOutputStream fileOut = new FileOutputStream("./tmp/" + file + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(list);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T load(String file) {
        T e = null;
        try {
            FileInputStream fileIn = new FileInputStream("./tmp/" + file + ".ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (T) in.readObject();
            System.out.println("deserialized " + e.getClass());
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException i) {
            System.out.println("Deserialized class not found");
            i.printStackTrace();
        }
        return e;
    }
}
