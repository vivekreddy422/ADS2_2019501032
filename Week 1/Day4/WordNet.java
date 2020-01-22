
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;

/**
 * WordNet
 */
public class WordNet {
    private final ArrayList<String> synset;
    private final Map<String, ArrayList<Integer>> M1;
    private final Digraph dg;
    private int[] result;
    private SAP sap;

    /**.
     * This constructor creates the data structure for synsets and hypernyms.
     * @param synsets the synonym set
     * @param hypernyms the hypernyms file
     */
    public WordNet(String synsets , String hypernyms){
        synset = new ArrayList<String>();
        M1 = new HashMap<String , ArrayList<Integer>>();
        parseSynsets(synsets);
        dg = new Digraph(synset.size());
        result = new int[synset.size()];
        parseHypernyms(hypernyms);
        if(!rootedDAG(result)){
            throw new IllegalArgumentException();
        }
        sap = new SAP(dg);
    }

    /**.
     * This method creates the data structure.
     * @param synsets the file
     */
    private void parseSynsets(String synsets) {
        In in = new In(synsets);
        while(in.hasNextLine()){
            String line = in.readLine();
            String[] temp = line.split(",");
            int id = Integer.parseInt(temp[0]);
            if(synset.size() < id){
                synset.ensureCapacity(id + 1);
            }
            synset.add(temp[1]);
            String[] names = temp[1].split(" ");
            for(String name : names){
                if(! M1.containsKey(name)){
                    M1.put(name , new ArrayList<Integer>());
                }
                M1.get(name).add(id);
            }
        }
        in.close();
    }

    /**.
     * This method creates the data structure.
     * @param hypernyms the file
     */
    public void parseHypernyms(String hypernyms) {
        In in = new In(hypernyms);
        while(in.hasNextLine()){
            String line = in.readLine();
            String[] temp = line.split(",");
            int ver = Integer.parseInt(temp[0]);
            result[ver] += temp.length - 1;
            for(int i = 1 ; i < temp.length ; i++){
                int word = Integer.parseInt(temp[i]);
                dg.addEdge(ver,word);
            }
        }
        in.close();
    }

    /**.
     * This method checks whether it is a rootedDag or not
     * @param result
     * @return returns true if it is a rootedDag.
     */
    private boolean rootedDAG(int[] result) {
        int count = 0;
        for(int i = 0 ; i < dg.V() ; ++i) {
            if(result[i] == 0) {
                ++count;
            }
        }
        if(count != 1) {
            return false;
        }
        return true;
    }

    /**
     * This method returns all the WordNet nouns
     * @return returns the wordnet nouns.
     */
    public Iterable<String> nouns() {
        return M1.keySet();
    }

    /**.
     * This method checks whether the given noun is a wordNet noun
     * @param word the noun to be checked
     * @return returns true if it is a WordNet noun and false otherwise
     */
    public boolean isNoun(String word) {
        if(word == null) {
            throw new java.lang.IllegalArgumentException();
        }
        return M1.containsKey(word);
    }

    /**.
     * This method yields the distance between nounA and nounB.
     * @param nounA the source noun
     * @param nounB the target noun
     * @return returns the shortest distance.
     */
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (M1.containsKey(nounA) && M1.containsKey(nounB)) {
            return sap.length(M1.get(nounA), M1.get(nounB));
        } else {
            throw new IllegalArgumentException("Word 1 : " + nounA + "and Word 2 : " + nounB);
        }
    }

    /**.
     * This method returns a synset that is the common ancestor of nounA 
     * and nounB in a shortest ancestral path 
     * @param nounA the source noun
     * @param nounB the target noun
     * @return the shortest ancestor.
     */
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            // throw new java.lang.IllegalArgumentException();
        }
        return synset.get(sap.ancestor(M1.get(nounA), M1.get(nounB)));
    }
}
