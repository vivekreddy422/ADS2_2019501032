public class Diagraph {
    Bag<Integer>[] adj;
    int v;
    int e;
    int[] indegree;
    public Diagraph(int v) {
        this.v = v;
        e = 0;
        adj = (Bag<Integer>[]) new Bag[v];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new Bag();
        }
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        indegree[w++];
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
        
    }
}
