import java.util.Arrays;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] arr = new char[256];
        for (int i = 0; i < 256; i++) {
            arr[i] = (char) i;
        }
        char[] copy = Arrays.copyOfRange(arr, 0, arr.length);
        String str = BinaryStdIn.readString();
        String s = "";
        int[] array = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            for (int j = 0; j < arr.length; j++) {
                if (c == arr[j]) {
                    array[i] = j;
                    for (int k = j; k > 0; k--) {
                        arr[k] = arr[k - 1];
                    }
                    arr[0] = c;
                    break;
                }
            }
        }
        for (int i = 0; i < array.length; i++) {
            BinaryStdOut.write(copy[array[i]]);
        }
        BinaryStdOut.close();     
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] arr = new char[256];
        for (int i = 0; i < 256; i++) {
            arr[i] = (char) i;
        }
        String str = BinaryStdIn.readString();
        String s = "";
        for (int i = 0; i < str.length(); i++) {
            int temp = (int) str.charAt(i);
            char c = arr[temp];
            BinaryStdOut.write(c);
            for (int k = temp; k > 0; k--) {
                arr[k] = arr[k - 1];
            }
            arr[0] = c;      
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }

}