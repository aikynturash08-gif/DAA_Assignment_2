@SuppressWarnings("unchecked")
public class DynamicArray<T> {
    private T[] data;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public DynamicArray() {
        data = (T[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public int size() { return size; }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > data.length) {
            int newCapacity = data.length * 2;
            if (newCapacity < minCapacity) newCapacity = minCapacity;
            T[] newData = (T[]) new Object[newCapacity];
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }

    public void add(T x) {
        ensureCapacity(size + 1);
        data[size++] = x;
    }

    public long add(int index, T x) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        ensureCapacity(size + 1);
        int numMoved = size - index;
        if (numMoved > 0) {
            System.arraycopy(data, index, data, index + 1, numMoved);
        }
        data[index] = x;
        size++;
        return numMoved;
    }

    public long remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(data, index + 1, data, index, numMoved);
        }
        data[--size] = null;
        return numMoved;
    }

    public T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return data[index];
    }

    public long contains(T x) {
        long comparisons = 0;
        for (int i = 0; i < size; i++) {
            comparisons++;
            if ((x == null && data[i] == null) || (x != null && x.equals(data[i]))) {
                return comparisons;
            }
        }
        return comparisons;
    }
}
