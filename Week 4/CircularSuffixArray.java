import edu.princeton.cs.algs4.Merge;

public class CircularSuffixArray {
 private final int n;
 private Suffix[] suffixes;
 private int[] index;

 public CircularSuffixArray(String s) {
     if (s == null){ throw new IllegalArgumentException();}
  // Initialize size n
  this.n = s.length();

  // Initialize suffixes array
  this.suffixes = new Suffix[n];
  for (int i = 0; i < n; i++) {
   suffixes[i] = new Suffix(s, i);
  }

  // Sort suffixes
  Merge.sort(suffixes);

  // Initialize index array
  this.index = new int[n];
  for (int i = 0; i < n; i++) {
   index[i] = suffixes[i].getIndex();
  }
 }
/*
 * @return int length of s
 * return the legth of the suffix
 */
 public int length(){return n;}

 /*
  * @param i index to get item from
  * @return int item at index i
  * returns the item at index 1
  */
 public int index(int i) {
     if (i < 0) {throw new IllegalArgumentException();}
     if (i > this.n-1) {throw new IllegalArgumentException();}
     return index[i];
 }

 private class Suffix implements Comparable<Suffix> {
  private String s;
  private final int index;

  /*
   * @param s the suffix to be initialized
   * @param index the index of the suffix
   */
  private Suffix(String s, int index) {
      if ( s == null) {throw new IllegalArgumentException();}
      if (index < 0) {throw new IllegalArgumentException();}
      if (index > n) {throw new IllegalArgumentException();}
      this.s = s;
      this.index = index;
  }

  /*
   * @return int index
   * return the index of the suffix
   */
  public int getIndex() {return index;}

  // Comparator for Mergesort
  /*
   * @param that suffix to compare to the suffix of the object
   * @return int -1 if that is greater than the object, 1 if that is less than the object, and 0 if they are the same
   * Interface to compare two suffixes
   */
  public int compareTo(Suffix that) {
      if (that == null) {throw new IllegalArgumentException();}
   if (this == that) return 0;
   int n = this.s.length();
   for (int i = 0; i < n; i++) {
    char cthis = s.charAt((this.index + i) % n);
    char cthat = s.charAt((that.index + i) % n);
    if (cthis < cthat) {return -1;}
    if (cthis > cthat) {return 1;}
   }
   return 0;
  }
 }
  public static void main(String[] args)
    {
    String test=args[0];
    CircularSuffixArray circSA=new CircularSuffixArray(test);
    int n=test.length();
    for (int i=0; i<test.length(); i++) {
      System.out.println(circSA.index(i));}
  }
}