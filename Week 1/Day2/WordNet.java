import java.util.*;
import java.util.Scanner;
import java.io.File;

public class WordNet {
    String[] synsets;
    String[] hypernyms;
    Hashtable<String, ArrayList<Integer>> ht;
    Hashtable<Integer, ArrayList<Integer>> ht2;
    public WordNet(String synsetsFileName, String hypernymsFileName) throws Exception {
        ht = new Hashtable<String, ArrayList<Integer>>();
        ht2 = new Hashtable<Integer, ArrayList<Integer>>();
        parseSynsets(synsetsFileName);
        parseHypernyms(hypernymsFileName);
        createDS();
    }

    /**.
     * This method creates the synsets array that reads the input from file synsets.txt
     * @param filename the synsets.txt from which the synsets are to be retrieved
     * @throws Exception exception
     */
    private void parseSynsets (String filename) throws Exception {
        ArrayList<String> lines = new ArrayList<String>();
        Scanner sc = new Scanner(new File(filename));
        while(sc.hasNext()) {
            lines.add(sc.nextLine());
        }
        synsets = lines.toArray(new String[lines.size()]);
    }

    /**.
     * This method creates the hypernyms array that reads the input from file hypernyms.txt
     * @param filename the hypernyms.txt from which the hypernyms are to be retrieved
     * @throws Exception exception
     */
    private void parseHypernyms (String filename) throws Exception {
        ArrayList<String> lines = new ArrayList<String>();
        Scanner sc = new Scanner(new File(filename));
        while(sc.hasNext()) {
            lines.add(sc.nextLine());
        }
        hypernyms = lines.toArray(new String[lines.size()]);
    }
    
    public void createDS() {
        ArrayList<Integer> temp;
        for (int i = 0; i < synsets.length; i++) {
            String[] each = synsets[i].split(",")[1].split(" ");
            for (int j = 0; j < each.length; j++) {
                if (ht.get(each[j]) != null) {
                    ht.get(each[j]).add(Integer.parseInt(synsets[i].split(",")[0]));
                    temp = ht.get(each[j]);
                } else {
                    temp = new ArrayList<Integer>();
                    temp.add(Integer.parseInt(synsets[i].split(",")[0]));
                }
                ht.put(each[j], temp);
            }
        }

        ArrayList<Integer> x;
        for (int i = 0; i < hypernyms.length; i++) {
            int id = Integer.parseInt(hypernyms[i].split(",")[0]);
            x = new ArrayList<Integer>();
            for (int j = 1; j < hypernyms[i].split(",").length; j++) {
                x.add(Integer.parseInt(hypernyms[i].split(",")[j]));
            }
            ht2.put(id, x);
        }
    }

    public static void main(String[] args) throws Exception{
        WordNet wn = new WordNet("synsets.txt", "hypernyms.txt");
        System.out.println(wn.ht);
        System.out.println(wn.ht2);
        System.out.println(wn.ht.size());
    }
}
