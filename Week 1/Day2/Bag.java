public class Bag {
    Node first;
    int size;
        class Node {
            int value;
            Node next;
            public Node(int x) {
                value = x;
                next = null;
            }
        }

    public Bag() {
        size = 0;
        first = null;
    }

    public void add(int x) {
        if (first == null) {
            first = new Node(x);
            size++;
            return;
        }
        Node temp = new Node(x);
        temp.next = first;
        first = temp;
        size++;
    }

    int size() {
        return size;
    }

    public String toString() {
        String str = "";
        for (Node x = first; x != null; x = x.next) {
            str = str + x.value;
        }
        return str;
    }
}
