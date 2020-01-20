public class Diagraph {
    Bag[] adj;
    int v;
    int e;
    public Diagraph(int v) {
        this.v = v;
        e = 0;
        adj = (Bag[]) new Bag[v];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new Bag();
        }
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        e++;
    }

    public int v() {
        return v;
    }

    public void printGraph() {
        for (int i = 0; i < adj.length; i++) {
            System.out.println(i + " --> " + adj[i]);
        }
    }

    public static void main(String[] args) throws Exception{
        WordNet wn = new WordNet("synsets.txt", "hypernyms.txt");
        // System.out.println(wn.ht);
        // System.out.println(wn.ht2);
        // System.out.println(wn.ht.size());
        System.out.println(wn.ht2.size());
        Diagraph dg = new Diagraph(wn.ht2.size());
        for (int i = 0; i < wn.ht2.size(); i++) {
            for (int j = 0; j < wn.ht2.get(i).size(); j++) {
                dg.addEdge(i, wn.ht2.get(i).get(j));
            }
        }
        System.out.println(dg.e);
        // dg.printGraph();
    }
}
