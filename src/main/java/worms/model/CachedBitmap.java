package worms.model;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.util.Hashtable;

/**
 * @author Václav Blažej
 */
public class CachedBitmap extends BufferedImage {

    public CachedBitmap(int width, int height, int imageType) {
        super(width, height, imageType);
    }

    public CachedBitmap(int width, int height, int imageType, IndexColorModel cm) {
        super(width, height, imageType, cm);
    }

    public CachedBitmap(ColorModel cm, WritableRaster raster, boolean isRasterPremultiplied, Hashtable<?, ?> properties) {
        super(cm, raster, isRasterPremultiplied, properties);
    }

    public CachedBitmap(BufferedImage bi) {
        this(bi.getColorModel(), bi.copyData(null), bi.getColorModel().isAlphaPremultiplied(), null);
    }

    public CachedBitmap deepCopy() {
        ColorModel cm = this.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = this.copyData(null);
        return new CachedBitmap(cm, raster, isAlphaPremultiplied, null);
    }

    private static class QuadTree {
    }
}
