import java.io.FileNotFoundException;
import edu.princeton.cs.algs4.In;

public class Outcast {
    private WordNet wordnet;
    /**
     * This constructor takes a WorNet object
     * @param wordnet the given object.
     */
    public Outcast(WordNet wordnet)  {
        this.wordnet = wordnet;
    }        

    /**.
     * This method returns an outcast based on the given array of WordNet nouns
     * @param nouns the array of WordNet nouns
     * @return returns the outcast
     */
    public String outcast(String[] nouns) {
        int max = 0;
        int index = -1;
        for (int i = 0; i < nouns.length; i++) {
            int dist = 0;
            for (int j = 0; j < nouns.length; j++) {
                dist += wordnet.distance(nouns[i], nouns[j]);
            }
            if (dist > max) {
                max = dist;
                index = i;
            }
        }
        return nouns[index];
    }   
}
