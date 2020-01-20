import java.util.*;
import java.io.File;
public class Solution {
    String[] emails;
    String[] email_logs;
    Hashtable<Integer, String> ht;
    Hashtable<Integer, Integer> ht2;
    Map<Integer, Integer> unsortedMap;
    
    public Solution(String emails, String email_logs) throws Exception {
        unsortedMap = new HashMap<>();
        ht = new Hashtable<Integer, String>();
        ht2 = new Hashtable<Integer, Integer>();
        parseEmails(emails);
        parseEmailLogs(email_logs);
        createDS();
    }

    public void parseEmails(String filename) throws Exception {
        ArrayList<String> lines = new ArrayList<String>();
        Scanner sc = new Scanner(new File(filename));
        while(sc.hasNext()) {
            lines.add(sc.nextLine());
        }
        emails = lines.toArray(new String[lines.size()]);
    }

    public void parseEmailLogs(String filename) throws Exception {
        ArrayList<String> lines = new ArrayList<String>();
        Scanner sc = new Scanner(new File(filename));
        while(sc.hasNext()) {
            lines.add(sc.nextLine());
        }
        email_logs = lines.toArray(new String[lines.size()]);
    }

    public void createDS() {
        for (int i = 0; i < emails.length; i++) {
            ht.put(Integer.parseInt(emails[i].split(";")[0]), emails[i].split(";")[1]);
        }
        for (int i = 0; i < email_logs.length; i++) {
            int v = Integer.parseInt(email_logs[i].split(",")[1].split(" ")[2]);
            
            if (ht2.get(v) == null) {
                ht2.put(v, 1);
                unsortedMap.put(v, 1);
            } else {
                int count = ht2.get(v);
                ht2.put(v, count + 1);
                unsortedMap.put(v, count + 1);
            }
        }
    }

    

    public void printTop(int n) {
        //Took the following code as reference from
        //https://howtodoinjava.com/sort/java-sort-map-by-values/

        //LinkedHashMap preserve the ordering of elements in which they are inserted
        LinkedHashMap<Integer, Integer> reverseSortedMap = new LinkedHashMap<>();
        
        //Use Comparator.reverseOrder() for reverse ordering
        unsortedMap.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) 
            .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
        
        // System.out.println("Reverse Sorted Map   : " + reverseSortedMap);

        int count = 0;
        for (int i : reverseSortedMap.keySet()) {
            if (count < n) {
                System.out.println(ht.get(i) + " : " + reverseSortedMap.get(i));
                count++;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Solution sol = new Solution("emails.txt", "email-logs.txt");
        // System.out.println(sol.ht2);
        // System.out.println(sol.tm);
        sol.printTop(10);
        
    }
}
