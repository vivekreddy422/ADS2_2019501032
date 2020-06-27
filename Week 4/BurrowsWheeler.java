import java.util.Arrays;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output 
    public static void transform() {
        // String str = BinaryStdIn.readString();
        // // System.out.println(str);
        // CircularSuffixArray obj = new CircularSuffixArray(str);
        // int ind = -1;
        // String s = "";
        // for (int i = 0; i < str.length(); i++) {
        //     String temp = str.substring(obj.index(i)) + str.substring(0, obj.index(i));
        //     s += temp.charAt(str.length() - 1);
        //     if (obj.index(i) == 0) {
        //         ind = i;
        //     }
        // }
        // // System.out.println(ind);
        // String sample = Integer.toHexString(ind);
        // if (sample.length() != 8) {
        //     for (int i = sample.length(); i < 8; i++) {
        //         sample = "0" + sample;
        //     }
        // }
        // // System.out.println(sample);
        // for (int i = 0; i < sample.length(); i += 2) {
        //     String stri = String.valueOf(sample.charAt(i));
        //     stri += sample.charAt(i + 1);
        //     int temp = Integer.parseInt(stri, 16);
        //     BinaryStdOut.write((char) temp);
        // }
        // BinaryStdOut.write(s);
        // BinaryStdOut.close();

        String s = BinaryStdIn.readString();
        int n = s.length();
        char[] t = new char[n];
        int first = 0;
        CircularSuffixArray csa = new CircularSuffixArray(s);
        while (first < n && csa.index(first) != 0) {
        first++;
        }
        BinaryStdOut.write(first);
        for (int i = 0; i < n; i++) {
        BinaryStdOut.write(s.charAt((csa.index(i) + s.length() - 1) % s.length()));
        t[i] = s.charAt((csa.index(i) + n - 1) % n);
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        // String so = BinaryStdIn.readString();
        // // System.out.println(so.length());
        // String str = so.substring(4);
        // // System.out.println(so.substring(0, 4));
        // String sample = "";
        // for (int i = 0; i < 4; i++) {
        //     sample += Integer.toHexString((int) so.charAt(i));
        // }
        // // System.out.println(sample);
        // int temp = Integer.parseInt(sample, 16);
        // // System.out.println(temp);
        // char[] or = str.toCharArray();
        // // System.out.println(Arrays.toString(or));
        // // System.out.println();
        // char[] ch = str.toCharArray();
        // Arrays.sort(ch);
        // boolean[] marker = new boolean[or.length];
        // // System.out.println(Arrays.toString(ch));
        // int[] next = new int[str.length()];
        // for (int i = 0; i < or.length; i++) {
        //     char c = ch[i];
        //     for (int j = 0; j < or.length; j++) {
        //         if (c == or[j] && !marker[j]) {
        //             next[i] = j;
        //             // or[j] = '\u00b9';
        //             marker[j] = true;
        //             break;
        //         }
        //     }
        // }
        // // System.out.println(Arrays.toString(marker));
        // // System.out.println(Arrays.toString(next));
        // String s = "";
        // int index = temp;
        // for (int i = 0; i < or.length; i++) {
        //     s += ch[index];
        //     index = next[index];
        // }
        // BinaryStdOut.write(s);
        // BinaryStdOut.close();
        int R = 256;
        int first = BinaryStdIn.readInt();
        String t = BinaryStdIn.readString();
        
        int n = t.length();

        int[] next = new int[n];

        int[] count = new int[R + 1];

        for (int i = 0; i < n; i++) {
            count[t.charAt(i) + 1]++;
        }
        for (int i = 1; i < R + 1; i++) {
            count[i] += count[i - 1];
        }
        for (int i = 0; i < n; i++) {
            next[count[t.charAt(i)]++] = i;
        }
        for (int i = next[first], c = 0; c < n; i = next[i], c++) {
            BinaryStdOut.write(t.charAt(i));
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if      (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
        else throw new IllegalArgumentException("Illegal command line argument");
    }

}