import java.util.Set;
import java.util.HashSet;
import java.lang.StringBuffer;

public class BoggleSolver {
    private TrieSET obj;
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        obj = new TrieSET();
        for (int i = 0; i < dictionary.length; i++) {
            obj.add(dictionary[i]);
        }
    }

    /**
     * This method returns all the valid words in a given Boggle board.
     * @param board the given Boggle board.
     * @return the valid words as an Iterable.
     */
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        Set<String> allValidwords = new HashSet<>();
        StringBuffer s = new StringBuffer();
        boolean[][] arr = new boolean[board.rows()][board.cols()];
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                checkWord(board, i, j, arr, allValidwords, s);
            }
        }
        return allValidwords;
    }

    private void checkWord(BoggleBoard board, int i, int j, boolean[][] arr, Set<String> validwords, StringBuffer s) {
        if (arr[i][j] == false) {
            arr[i][j] = true;
            s.append(board.getLetter(i, j));
            if (board.getLetter(i, j) == 'Q') {
                s.append("U");
            }
            if (!obj.hasPrefix(s.toString())) {
                if (s.toString().equals("QU")) {
                    s.deleteCharAt(s.length() - 1);
                    s.deleteCharAt(s.length() - 1);
                } else {
                    s.deleteCharAt(s.length() - 1);
                }
                arr[i][j] = false;
                return;
            }
            if (s.length() >= 3) {
                if (obj.contains(s.toString())) {
                        validwords.add(s.toString());
                }
            }
            for (int m = -1; m <= 1; m++) {
                for (int n = -1; n <= 1; n++) {
                    if (m == 0 && n == 0) {
                        continue;
                    }
                    if ((i + m >= 0) && (i + m < board.rows()) && (j + n >= 0) && (j + n < board.cols())) {
                        checkWord(board, i + m, j + n, arr, validwords, s);
                    }
                }
            }
            arr[i][j] = false;
            s.deleteCharAt(s.length() - 1);
        }
        
    }

    /**
     * This method returns the score of the given word if it is in the dictionary.
     * @param word the word for the score is to be calculated
     * @return returns the score.
     */
    public int scoreOf(String word) {
        if (!obj.contains(word)) {
            return 0;
        }
        if (word.length() >= 3 && word.length() <= 4) {
            return 1;
        }
        if (word.length() == 5) {
            return 2;
        }
        if (word.length() == 6) {
            return 3;
        }
        if (word.length() == 7) {
            return 5;
        }
        if (word.length() >= 8) {
            return 11;
        }
        return 0;
    }
}
