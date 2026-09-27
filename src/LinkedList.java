public class LinkedList<T> {
    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) { this.data = data; }
    }

    private Node<T> head;
    private int size;

    public int size() { return size; }

    public void add(T x) {
        add(size, x);
    }

    public long add(int index, T x) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        Node<T> newNode = new Node<>(x);
        long accesses = 0;

        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> prev = head;
            for (int i = 0; i < index - 1; i++) {
                prev = prev.next;
                accesses++;
            }
            newNode.next = prev.next;
            prev.next = newNode;
            accesses++;
        }
        size++;
        return accesses; 
    }

    public long remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        long accesses = 0;

        if (index == 0) {
            head = head.next;
        } else {
            Node<T> prev = head;
            for (int i = 0; i < index - 1; i++) {
                prev = prev.next;
                accesses++;
            }
            prev.next = prev.next.next;
            accesses++;
        }
        size--;
        return accesses;
    }

    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node<T> curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr.data;
    }

    public long contains(T x) {
        long comparisons = 0;
        Node<T> curr = head;
        while (curr != null) {
            comparisons++;
            if ((x == null && curr.data == null) || (x != null && x.equals(curr.data))) {
                return comparisons;
            }
            curr = curr.next;
        }
        return comparisons;
    }
}
