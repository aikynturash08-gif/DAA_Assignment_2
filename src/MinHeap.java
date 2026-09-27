@SuppressWarnings("unchecked")
public class MinHeap<T extends Comparable<T>> {
    private T[] heap;
    private int size;
    private long totalComparisons = 0;

    public MinHeap() {
        heap = (T[]) new Comparable[10];
        size = 0;
    }

    public int size() { return size; }
    public long getTotalComparisons() { return totalComparisons; }
    public void resetComparisons() { totalComparisons = 0; }

    private void ensureCapacity() {
        if (size >= heap.length) {
            T[] newHeap = (T[]) new Comparable[heap.length * 2];
            System.arraycopy(heap, 0, newHeap, 0, size);
            heap = newHeap;
        }
    }

    public void insert(T x) {
        ensureCapacity();
        heap[size] = x;
        siftUp(size);
        size++;
    }

    public T peekMin() {
        if (size == 0) throw new IllegalStateException("Heap is empty");
        return heap[0];
    }

    public T extractMin() {
        if (size == 0) throw new IllegalStateException("Heap is empty");
        T min = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        if (size > 0) {
            siftDown(0);
        }
        return min;
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            totalComparisons++;
            if (heap[index].compareTo(heap[parent]) < 0) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    private void siftDown(int index) {
        while (index * 2 + 1 < size) {
            int left = index * 2 + 1;
            int right = left + 1;
            int smallest = left;

            if (right < size) {
                totalComparisons++;
                if (heap[right].compareTo(heap[left]) < 0) {
                    smallest = right;
                }
            }

            totalComparisons++;
            if (heap[index].compareTo(heap[smallest]) > 0) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}
