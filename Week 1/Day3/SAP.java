import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class SAP{
   private Digraph dg;

   /**.
    * SAP class constructor
    * @param d the diagraph to be passed.
    */
   public SAP(Digraph d){
      if(d ==  null){
         throw new IllegalArgumentException();
      }
      this.dg = d;
   }

   /**.
    * This method checks whether the vertex is in the specified limits
    * @param v the vertex to be checked.
    * @return returne true if in the limits and false otherwise
    */
   private boolean checkBounds(int v){
         return 0 <= v && v < dg.V();
   }

   /**
    * This method determines the length of the shortest ancestral path between v and w
    * @param V the sorce vertex V
    * @param W the target vertex W
    * @return  returns the path and returns -1 if no such path
    */
   public int length(int V , int W){
      if (!checkBounds(V) || !checkBounds(W)) {
         throw new java.lang.IndexOutOfBoundsException();
      }
      BreadthFirstDirectedPaths v = new BreadthFirstDirectedPaths(dg, V);
      BreadthFirstDirectedPaths w = new BreadthFirstDirectedPaths(dg, W);
      int min = Integer.MAX_VALUE;
      int flag = 0;
      for (int i = 0; i < dg.V(); i++) {
         if (!v.hasPathTo(i) || !w.hasPathTo(i)) {
               continue;
         }
         int dist = v.distTo(i) + w.distTo(i);
         if (dist < min) {
               min = dist;
               flag = 1;
         }
      }
      if (flag == 0) {
         return -1;
      }
      return min;
   }

   /**.
    * This method returns the ancestor pertaining to the shortest length
    * @param v the group of vertices 
    * @param w the group of vertices
    * @return the shortest ancestor
    */
   public int ancestor(Iterable<Integer> v, Iterable<Integer>w){
      if (v == null || w == null) {
         throw new IllegalArgumentException();
      }
      int min = Integer.MAX_VALUE;
      int flag = 0;
      int anc = -1;
      BreadthFirstDirectedPaths vb = new BreadthFirstDirectedPaths(dg, v);
      BreadthFirstDirectedPaths wb = new BreadthFirstDirectedPaths(dg, w);
      for (int i = 0; i < dg.V(); i++) {
         if (vb.hasPathTo(i) && wb.hasPathTo(i)) {
            int dist = vb.distTo(i) + wb.distTo(i);
            if (dist < min) {
               min = dist;
               anc = i;
               flag = 1;
            }
         }
      }
      if (flag == 0) {
         return -1;
      }
      return anc;
   }

   /**.
    * This method returns the ancestor pertaining to the shortest length
    * @param v a vetex
    * @param w another vertex
    * @return the shortest ancestor
    */
   public int ancestor(int V, int W) {
      if (!((V >= 0 && V <= dg.V() - 1) && (W >= 0 && W <= dg.V() - 1))) {
         throw new IllegalArgumentException();
      }
      int min = Integer.MAX_VALUE;
      int anc = -1;
      BreadthFirstDirectedPaths v = new BreadthFirstDirectedPaths(dg, V);
      BreadthFirstDirectedPaths w = new BreadthFirstDirectedPaths(dg, W);
      for (int i = 0; i < dg.V(); i++) {
         if (v.hasPathTo(i) && w.hasPathTo(i)) {
            int dist = v.distTo(i) + w.distTo(i);
            if (dist < min) {
               min = dist;
               anc = i;
            }
         }
      }
      if (min == Integer.MAX_VALUE) {
         return -1;
      }
      return anc;
   }

   /**
    * This method determines the length of the shortest ancestral path between v and w
    * @param V the sorce vertex V
    * @param W the target vertex W
    * @return  returns the path and returns -1 if no such path
    */
   public int length(Iterable<Integer> v, Iterable<Integer> w) {
      int sp = Integer.MAX_VALUE;
      for(int i : v){
         for(int j : w){
            int len = length(i,j);
            if( len != -1 && sp > len){
               sp = len;
            }
         }
      }
      if(sp != Integer.MAX_VALUE) {
         return sp;
      } else {
         return -1;
      }
   }

   // public static void main(String[] args) {
   //    In input = new In();
   //    Digraph graph = new Digraph(input);
   //    SAP s = new SAP(graph);
   //    System.out.println("length: " + s.length(3, 11) + " Ancestor: " + s.ancestor(3, 11));
   //    System.out.println("length: " + s.length(1, 6) + " Ancestor: " + s.ancestor(1, 6));
   // }
}