import edu.princeton.cs.algs4.Picture;
import java.awt.Color;
import java.io.File;
import java.util.Arrays;
public class SeamCarver {

    Picture picture;
    double[][] engMatrix;
    // create a seam carver object based on the given picture
    public SeamCarver(final Picture picture) {
        this.picture = new Picture(picture);
        engMatrix = new double[height()][width()];
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

    private double[][] cummMatrix(double[][] engMatrix) {
        double[][] cumm = new double[height()][width()];
        for (int i = 0; i < width(); i++) {
            cumm[0][i] = 1000.0;
        }
        for (int r = 1; r < height(); r++) {
            for (int c = 0; c < width(); c++) {
                if (c == 0) {
                    if (cumm[r - 1][c] < cumm[r - 1][c + 1]) {
                        cumm[r][c] = engMatrix[r][c] + cumm[r - 1][c];
                    } else {
                        cumm[r][c] = engMatrix[r][c] + cumm[r - 1][c + 1];
                    }
                } else if (c == width() - 1) {
                    if (cumm[r - 1][c] < cumm[r - 1][c - 1]) {
                        cumm[r][c] = engMatrix[r][c] + cumm[r - 1][c];
                    } else {
                        cumm[r][c] = engMatrix[r][c] + cumm[r - 1][c - 1];
                    }
                } else {
                    if (cumm[r - 1][c - 1] <= cumm[r - 1][c] && cumm[r - 1][c - 1] <= cumm[r - 1][c + 1]) {
                        cumm[r][c] = engMatrix[r][c] + cumm[r - 1][c - 1];
                    } else if(cumm[r - 1][c] <= cumm[r - 1][c + 1]) {
                        cumm[r][c] = engMatrix[r][c] + cumm[r - 1][c];
                    } else {
                        cumm[r][c] = engMatrix[r][c] + cumm[r - 1][c + 1];
                    }
                }
            }
        }
        return cumm;
    }
    // sequence of indices for horizontal seam
    // public int[] findHorizontalSeam() {
    // }

    // // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] ver = new int[height()];
        double[][] cumm = cummMatrix(engMatrix);
        double min = Double.MAX_VALUE;
        int pos = -1;
        for (int i = 0; i < cumm[cumm.length - 1].length; i++) {
            if (min > cumm[cumm.length - 1][i]) {
                min = cumm[cumm.length - 1][i];
                pos = i;
            }
        }
        // ver[ver.length - 1] = pos;
        for (int r = cumm.length - 1; r >= 0; r--) {
            ver[r] = pos;
            if (r == 0) {
                break;
            }
            if (pos == 0) {
                if (cumm[r - 1][pos] > cumm[r - 1][pos + 1]) {
                    pos += 1;
                }
            } else if (pos == width() - 1) {
                if (cumm[r - 1][pos] > cumm[r - 1][pos - 1]) {
                    pos -= 1;
                }
            } else {
                if (cumm[r - 1][pos - 1] <= cumm[r - 1][pos] && cumm[r - 1][pos - 1] <= cumm[r - 1][pos + 1]) {
                    pos -= 1;
                } else if(cumm[r - 1][pos] <= cumm[r - 1][pos + 1]) {
                    
                } else {
                    pos += 1;
                }
            }
        }
        return ver;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(final int[] seam) {
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(final int[] seam) {
    }

    // unit testing (optional)
    public static void main(final String[] args) {
        Picture pic = new Picture(new File("E:\\Java\\ADS2_2019501032\\Week 2\\seam\\5x6.png"));
        SeamCarver sc = new SeamCarver(pic);
        System.out.println(sc.height());
        for (int row = 0; row < sc.height(); row++) {
            for (int col = 0; col < sc.width(); col++) {
                sc.engMatrix[row][col] = sc.energy(col, row);
                // System.out.print(sc.energy(col, row) + "  ");
            }
            // System.out.println();
        }
        double[][] cumm = sc.cummMatrix(sc.engMatrix);
        for (int i = 0; i < cumm.length; i++) {
            System.out.println(Arrays.toString(cumm[i]));
        }
        
        System.out.println(Arrays.toString(sc.findVerticalSeam()));
    }
}
