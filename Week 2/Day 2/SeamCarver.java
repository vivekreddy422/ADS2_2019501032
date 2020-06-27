import edu.princeton.cs.algs4.Picture;
import java.awt.Color;
import java.io.File;
import java.util.Arrays;
import java.lang.IllegalArgumentException;
/**.
 * The class Seam Carver is about resizing images. 
 */
public class SeamCarver {

    private Picture picture;
    private double[][] engMatrix;
    // create a seam carver object based on the given picture
    /**.
     * Initializes the picture and creates the energy matrix.
     * @param picture the picture to be resized.
     */
    public SeamCarver(final Picture picture) {
        this.picture = new Picture(picture);
        createEnergy();
    }

    /**.
     * This is a helper method to calculate the energy at each pixel
     * and creating the energy matrix. 
     */
    private void createEnergy() {
        engMatrix = new double[height()][width()];
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                engMatrix[row][col] = energy(col, row);
            }
        }
    }

    /**.
     * This method returns the current picture.
     * @return the current picture.
     */
    public Picture picture() {
        return picture;
    }

    /**.
     * This method returns the width of the picture.
     * @return the number of rows
     */
    public int width() {
        return picture.width();
    }

    /**.
     * This method returns the height of the picture.
     * @return the number of rows.
     */
    public int height() {
        return picture.height();
    }

    /**.
     * This method returns the red value of the pixel.
     * @param rgb the rgb value of the pixel.
     * @return the red value as integer.
     */
    private int getRed(int rgb) {
        return (rgb >> 16) & 0xFF;
    }

    /**.
     * This method returns the green value of the pixel.
     * @param rgb the rgb value of the pixel
     * @return the green value as integer
     */
    private int getGreen(int rgb) {
        return (rgb >> 8) & 0xFF;
    }

    /**.
     * This method returns the blue value of the pixel.
     * @param rgb the rgb value of the pixel
     * @return the blue value as integer
     */
    private int getBlue(int rgb) {
        return (rgb >> 0) & 0xFF;
    }

    /**.
     * This method calculates the energy at the pixel
     * @param x the column x
     * @param y the row y
     * @return the calculated energy at (x,y)
     */
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

    /**.
     * This method calculates the cummilative energies and returns
     * the cummilative energy matrix
     * @param engMatrix the original energy matrix
     * @return the updated cummilative energy matrix
     */
    private double[][] cummMatrix(double[][] engMatrix) {
        double[][] cumm = new double[engMatrix.length][engMatrix[0].length];
        for (int i = 0; i < engMatrix[0].length; i++) {
            cumm[0][i] = 1000.0;
        }
        for (int r = 1; r < engMatrix.length; r++) {
            for (int c = 0; c < engMatrix[0].length; c++) {
                if (c == 0) {
                    if (cumm[r - 1][c] < cumm[r - 1][c + 1]) {
                        cumm[r][c] = engMatrix[r][c] + cumm[r - 1][c];
                    } else {
                        cumm[r][c] = engMatrix[r][c] + cumm[r - 1][c + 1];
                    }
                } else if (c == engMatrix[0].length - 1) {
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

    /**.
     * This method determines the transpose of the given matrix
     * @param arr the cummilative energy matrix
     * @return the transpose of the given matrix
     */
    private double[][] transpose(double[][] arr) {
        double[][] tr = new double[arr[0].length][arr.length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                tr[j][i] = arr[i][j];
            }
        }
        return tr;
    }

    /**.
     * This method returns the sequence of indices for the horizontal seam
     * @return the integer array containing the indices
     */
    public int[] findHorizontalSeam() {
        if (engMatrix.length == 1) {
            int[] arr = new int[engMatrix[0].length];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = 0;
            }
            return arr;
        }
        return vertices(transpose(engMatrix));
    }

    /**.
     * This method returns the sequence of indices for the vertical seam
     * @return the integer array containing the indices
     */
    public int[] findVerticalSeam() {
        if (engMatrix[0].length == 1) {
            int[] arr = new int[engMatrix.length];
            for (int i = 0; i < engMatrix.length; i++) {
                arr[i] = 0;
            }
            return arr;
        }
        return vertices(engMatrix);
        
    }

    /**.
     * This is a helper method which determines the indices on the seam.
     * @param energy the updated cummilative energy matrix
     * @return the integer array of indices
     */
    private int[] vertices(double[][] energy) {
        int[] ver = new int[energy.length];
        double[][] cumm = cummMatrix(energy);
        double min = Double.MAX_VALUE;
        int pos = -1;
        for (int i = 0; i < cumm[cumm.length - 1].length; i++) {
            if (min > cumm[cumm.length - 1][i]) {
                min = cumm[cumm.length - 1][i];
                pos = i;
            }
        }
        for (int r = cumm.length - 1; r >= 0; r--) {
            ver[r] = pos;
            if (r == 0) {
                break;
            }
            if (pos == 0) {
                if (cumm[r - 1][pos] > cumm[r - 1][pos + 1]) {
                    pos += 1;
                }
            } else if (pos == cumm[0].length - 1) {
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

    /**.
     * This method removes the horizontal seam from the current picture
     * @param seam the array of indices to be removed from the picture
     */
    public void removeHorizontalSeam(final int[] seam) {
        if (seam.length > engMatrix[0].length || seam == null || engMatrix.length <= 1) {
            //throw exception
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < seam.length - 1; i++) {
            if ( !(seam[i + 1] - seam[i] >= -1 || seam[i + 1] - seam[i] <= 1)) {
                throw new IllegalArgumentException();
            }
        }
        double[][] aux = new double[engMatrix.length - 1][engMatrix[0].length];
        Picture pic = new Picture(engMatrix[0].length, engMatrix.length - 1);
        int flag;
        for (int c = 0; c < aux[0].length; c++) {
            flag = 0;
            for (int r = 0; r < aux.length && flag == 0; r++) {
                if (r < seam[c]) {
                    aux[r][c] = engMatrix[r][c];
                    pic.set(c, r, picture.get(c, r));
                } else if (r == seam[c]) {
                    flag = 1;
                    for (; r < aux.length; r++) {
                        aux[r][c] = engMatrix[r + 1][c];
                        pic.set(c, r, picture.get(c, r + 1));
                    }
                }
            }
        }
        engMatrix = aux;
        picture = pic;
    }

    // remove vertical seam from current picture
    /**.
     * This method removes the vertical seam from the current picture
     * @param seam the array of indices to be removed from the picture
     */
    public void removeVerticalSeam(final int[] seam) {
        if (seam.length > engMatrix.length || seam == null || engMatrix[0].length <= 1) {
            //throw exception
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < seam.length - 1; i++) {
            if ( !(seam[i + 1] - seam[i] >= -1 || seam[i + 1] - seam[i] <= 1)) {
                throw new IllegalArgumentException();
            }
        }
        double[][] aux = new double[engMatrix.length][engMatrix[0].length - 1];
        Picture pic = new Picture(engMatrix[0].length - 1, engMatrix.length);
        int flag;
        for (int r = 0; r < aux.length; r++) {
            flag = 0;
            for (int c = 0; c < aux[0].length && flag == 0; c++) {
                if (c < seam[r]) {
                    aux[r][c] = engMatrix[r][c];
                    pic.set(c, r, picture.get(c, r));
                } else if (c == seam[r]) {
                    flag = 1;
                    for (; c < aux[0].length; c++) {
                        aux[r][c] = engMatrix[r][c + 1];
                        pic.set(c, r, picture.get(c + 1, r));
                    }
                }
            }
        }
        engMatrix = aux;
        picture = pic;
    }

    // unit testing (optional)
    public static void main(final String[] args) {
        Picture pic = new Picture(new File("E:\\Java\\ADS2_2019501032\\Week 2\\seam\\3x4.png"));
        SeamCarver sc = new SeamCarver(pic);
        sc.removeHorizontalSeam(sc.findHorizontalSeam());
        for (int i = 0; i < sc.engMatrix.length; i++) {
            System.out.println(Arrays.toString(sc.engMatrix[i]));
        }
    }
    // String path = "E:\\Java\\ADS2_2019501032\\Week 2\\seam\\";
    //     File folder = new File(path);
    //     File[] ar = folder.listFiles();
    //     int count = 0;
    //     int inCount = 0;
    //     for (int i = 0; i < ar.length; i++) {
    //         if (ar[i].isFile() && ar[i].getName().endsWith("png")) {
    //             inCount++;
    //             Picture p = new Picture(path + ar[i].getName());
    //             SeamCarver w = new SeamCarver(p);

    //             int[] se = w.findVerticalSeam();
    //             String seam = "Vertical";

    //             // int[] se = w.findHorizontalSeam();
    //             // String seam = "Horizontal";

    //             File f = null;
    //             Scanner scObj = null;
    //             try {
    //                 f = new File(path + ar[i].getName().replace(".png", "") + ".printseams.txt");
    // //                 f = new File(path + "5x6"+ ".printseams.txt");
    //                 scObj = new Scanner(f);
    //             } catch (Exception e) {
    //                 count++;
    //                 continue;
    //             }

    //             boolean flag = false;
    //             String str = "";
    //             while (scObj.hasNext() && !flag) {
    //                 while ((str = scObj.nextLine()).startsWith(seam)) {
    //                     flag = true;
    //                     break;
    //                 }
    //         }
    //             String res = Arrays.toString(se).substring(0);
    //             res = res.replace(",", "").replace("[", "{ ").replace("]", " }");
    //             // String act = scObj.nextLine().replace("Vertical seam: ", "").substring(0);
    //             String act = str.replace(seam + " seam: ", "").substring(0);

    //             if (res.equals(act)) {
    //                 count++;
    //             } else {
    //                 System.out.println();
    //                 for (int k = 0; k < w.cummMatrix(w.transpose(w.engMatrix)).length; k++){
    //                     for(int l = 0; l < w.cummMatrix(w.transpose(w.engMatrix))[0].length; l++) {
    //                         System.out.printf("%.2f",  w.cummMatrix(w.transpose(w.engMatrix))[k][l]);
    //                         System.out.print(" ");
    //                     }
    //                     System.out.println();
    //                 }
    //                 System.out.println("Input : " + ar[i].getName());
    //                 System.out.println("Actual : " + act);
    //                 System.out.println("Obtained : " + res);
    //             }
    //             scObj.close();

    //         }
    //     }

    //     if (count != inCount) {
    //         System.out.println("Test cases passed : " + count + " out of " + inCount);
    //     } else {
    //         System.out.println("All Test Cases Passed");
    //     }

    // }
}
