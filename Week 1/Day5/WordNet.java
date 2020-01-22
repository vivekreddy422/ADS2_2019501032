
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

    private void parseHypernyms(String hypernyms) {
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

    public Iterable<String> nouns() {
        return M1.keySet();
    }

    public boolean isNoun(String word) {
        if(word == null) {
            throw new java.lang.IllegalArgumentException();
        }
        return M1.containsKey(word);
    }

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

    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new java.lang.IllegalArgumentException();
        }
        return synset.get(sap.ancestor(M1.get(nounA), M1.get(nounB)));
    }

    public static void main(String[] args){
        String filename1 = "E:\\Java\\ADS2_2019501032\\Week 1\\WordNet\\synsets.txt";
        String filename2 = "E:\\Java\\ADS2_2019501032\\Week 1\\WordNet\\hypernyms.txt";
        WordNet object = new WordNet(filename1, filename2);
    }
}
