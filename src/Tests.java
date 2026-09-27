public class Tests {
    public static void main(String[] args) {
        testDynamicArray();
        testLinkedList();
        testMinHeap();
        System.out.println("ALL TESTS PASSED SUCCESSFULLY!");
    }

    private static void testDynamicArray() {
        DynamicArray<Integer> arr = new DynamicArray<>();
        try { arr.get(0); } catch (IndexOutOfBoundsException e) { /* expected */ }

        arr.add(10);
        arr.add(20);
        assert arr.get(0) == 10;
        assert arr.get(1) == 20;

        arr.add(0, 5); // [5, 10, 20]
        assert arr.get(0) == 5;
        assert arr.remove(0) == 5;
        assert arr.size() == 2;
    }

    private static void testLinkedList() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(100);
        list.add(0, 50); // [50, 100]
        assert list.contains(50) > 0;
        assert list.remove(0) == 50;
        assert list.size() == 1;
    }

    private static void testMinHeap() {
        MinHeap<Integer> heap = new MinHeap<>();
        int[] input = {15, 3, 10, 1, 8, 3}; // Contains duplicates
        for (int x : input) heap.insert(x);

        int prev = heap.extractMin();
        while (heap.size() > 0) {
            int curr = heap.extractMin();
            assert prev <= curr : "Min-Heap constraint failed";
            prev = curr;
        }
    }
}
