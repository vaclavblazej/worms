package worms;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
import java.net.URL;
import java.util.Random;
import java.util.function.Supplier;

/**
 * @author Václav Blažej
 */
public final class Common {

    public static Random random = new Random();
    private static final Supplier<Float> func = () -> ((random.nextFloat() % 0.4f) + 0.6f) % 1;

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

    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public static BufferedImage loadImage(String fileName) {
        try {
            URL file = Common.class.getClassLoader().getResource(fileName);
            if (file == null) return null;
            return ImageIO.read(file);
        } catch (IOException ignored) {
            return null;
        }
    }

    public static Double getRandomRadian() {
        return 2 * Math.PI * random.nextDouble();
    }

    public static boolean getRandomBoolean(double pst) {
        return random.nextDouble() < pst;
    }
}
