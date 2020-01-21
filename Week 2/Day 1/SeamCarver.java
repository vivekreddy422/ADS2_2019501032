import edu.princeton.cs.algs4.Picture;
import java.awt.Color;
import java.io.File;
public class SeamCarver {

    Picture picture;
    // create a seam carver object based on the given picture
    public SeamCarver(final Picture picture) {
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    private int getRed(int rgb) {
        return (rgb >> 16) & 0xFF;
    }

    private int getGreen(int rgb) {
        return (rgb >> 8) & 0xFF;
    }

    private int getBlue(int rgb) {
        return (rgb >> 0) & 0xFF;
    }

    // energy of pixel at column x and row y
    public double energy(final int x, final int y) {
        if (x == 0 || y == 0 || x == picture.width() - 1 || y == picture.height() - 1) {
            return 1000.0;
        }
        int rx = getRed(picture.getRGB(x + 1, y)) - getRed(picture.getRGB(x - 1, y));
        int gx = getGreen(picture.getRGB(x + 1, y)) - getGreen(picture.getRGB(x - 1, y));
        int bx = getBlue(picture.getRGB(x + 1, y)) - getBlue(picture.getRGB(x - 1, y));
        double x2_comp = (double) ((rx * rx) + (gx * gx) + (bx * bx));
        int ry = getRed(picture.getRGB(x, y + 1)) - getRed(picture.getRGB(x, y - 1));
        int gy = getGreen(picture.getRGB(x, y + 1)) - getGreen(picture.getRGB(x, y - 1));
        int by = getBlue(picture.getRGB(x, y + 1)) - getBlue(picture.getRGB(x, y - 1));
        double y2_comp = (double) ((ry * ry) + (gy * gy) + (by * by));
        return Math.pow(x2_comp + y2_comp, 0.5);
    }

    // sequence of indices for horizontal seam
    // public int[] findHorizontalSeam() {
    // }

    // // sequence of indices for vertical seam
    // public int[] findVerticalSeam() {
    // }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(final int[] seam) {
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(final int[] seam) {
    }

    // unit testing (optional)
    public static void main(final String[] args) {
        Picture pic = new Picture(new File("E:\\Java\\ADS2_2019501032\\Week 2\\Day 1\\seam\\5x6.png"));
        SeamCarver sc = new SeamCarver(pic);
        System.out.println("Width : " + sc.width());
        System.out.println("Height : " + sc.height());
        System.out.println("Energy : " + sc.energy(3, 4));

        for (int row = 0; row < sc.height(); row++) {
            for (int col = 0; col < sc.width(); col++) {
                System.out.print(sc.energy(col, row) + "  ");
            }
            System.out.println();
        }    
    }
}
