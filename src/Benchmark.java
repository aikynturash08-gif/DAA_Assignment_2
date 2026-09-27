import java.util.Random;

public class Benchmark {
    private static final int SEED = 42;
    private static final int REPETITIONS = 5;
    private static final int[] N_SIZES = {100, 1000, 10000, 100000};

    public static void main(String[] args) {
        System.out.println("=== BENCHMARK START ===");
        runWorkload1();
        runWorkload2();
        runWorkload3();
        runWorkload4();
        System.out.println("=== BENCHMARK COMPLETE ===");
    }

    private static void runWorkload1() {
        System.out.println("\n--- Workload 1: Random Access ---");
        for (int n : N_SIZES) {
            long timeArray = 0, timeList = 0;
            for (int r = 0; r < REPETITIONS; r++) {
                Random rng = new Random(SEED + r);
                DynamicArray<Integer> arr = new DynamicArray<>();
                LinkedList<Integer> list = new LinkedList<>();
                for (int i = 0; i < n; i++) {
                    int val = rng.nextInt();
                    arr.add(val);
                    list.add(val);
                }

                int m = 10000;
                int[] indices = new int[m];
                for (int i = 0; i < m; i++) indices[i] = rng.nextInt(n);

                long start = System.nanoTime();
                for (int idx : indices) arr.get(idx);
                timeArray += (System.nanoTime() - start);

                start = System.nanoTime();
                for (int idx : indices) list.get(idx);
                timeList += (System.nanoTime() - start);
            }
            System.out.printf("N = %6d | DynamicArray: %8.3f ms | LinkedList: %8.3f ms\n",
                    n, (timeArray / (double) REPETITIONS) / 1e6, (timeList / (double) REPETITIONS) / 1e6);
        }
    }

    private static void runWorkload2() {
        System.out.println("\n--- Workload 2: Search ---");
        for (int n : N_SIZES) {
            long timeArr = 0, timeList = 0, compsArr = 0, compsList = 0;
            for (int r = 0; r < REPETITIONS; r++) {
                Random rng = new Random(SEED + r);
                DynamicArray<Integer> arr = new DynamicArray<>();
                LinkedList<Integer> list = new LinkedList<>();
                for (int i = 0; i < n; i++) {
                    int val = rng.nextInt();
                    arr.add(val);
                    list.add(val);
                }

                int[] searchVals = new int[1000];
                for (int i = 0; i < 1000; i++) searchVals[i] = rng.nextInt();

                long start = System.nanoTime();
                for (int val : searchVals) compsArr += arr.contains(val);
                timeArr += (System.nanoTime() - start);

                start = System.nanoTime();
                for (int val : searchVals) compsList += list.contains(val);
                timeList += (System.nanoTime() - start);
            }
            System.out.printf("N = %6d | Array Time: %8.3f ms (Comps: %d) | List Time: %8.3f ms (Comps: %d)\n",
                    n, (timeArr / (double) REPETITIONS) / 1e6, compsArr / REPETITIONS,
                    (timeList / (double) REPETITIONS) / 1e6, compsList / REPETITIONS);
        }
    }

    private static void runWorkload3() {
        System.out.println("\n--- Workload 3: Insert/Remove (Index 0 vs Index N/2) ---");
        for (int n : N_SIZES) {
            long tArr0 = 0, tList0 = 0, tArrMid = 0, tListMid = 0;

            for (int r = 0; r < REPETITIONS; r++) {
                Random rng = new Random(SEED + r);
                DynamicArray<Integer> arr = new DynamicArray<>();
                LinkedList<Integer> list = new LinkedList<>();
                for (int i = 0; i < n; i++) {
                    int val = rng.nextInt();
                    arr.add(val);
                    list.add(val);
                }

                // Insertion at index 0
                long start = System.nanoTime();
                for (int i = 0; i < 1000; i++) arr.add(0, 999);
                tArr0 += (System.nanoTime() - start);

                start = System.nanoTime();
                for (int i = 0; i < 1000; i++) list.add(0, 999);
                tList0 += (System.nanoTime() - start);

                // Re-build
                arr = new DynamicArray<>();
                list = new LinkedList<>();
                for (int i = 0; i < n; i++) {
                    int val = rng.nextInt();
                    arr.add(val);
                    list.add(val);
                }

                // Insertion at index N/2
                start = System.nanoTime();
                for (int i = 0; i < 1000; i++) arr.add(arr.size() / 2, 999);
                tArrMid += (System.nanoTime() - start);

                start = System.nanoTime();
                for (int i = 0; i < 1000; i++) list.add(list.size() / 2, 999);
                tListMid += (System.nanoTime() - start);
            }

            System.out.printf("N = %6d | Add(0) Array: %6.3f ms, List: %6.3f ms | Add(N/2) Array: %6.3f ms, List: %6.3f ms\n",
                    n, (tArr0 / (double) REPETITIONS) / 1e6, (tList0 / (double) REPETITIONS) / 1e6,
                    (tArrMid / (double) REPETITIONS) / 1e6, (tListMid / (double) REPETITIONS) / 1e6);
        }
    }

    private static void runWorkload4() {
        System.out.println("\n--- Workload 4: Priority Processing (Min-Heap) ---");
        for (int n : N_SIZES) {
            long tInsert = 0, tExtract = 0, totalComps = 0;

            for (int r = 0; r < REPETITIONS; r++) {
                Random rng = new Random(SEED + r);
                MinHeap<Integer> heap = new MinHeap<>();
                int[] data = new int[n];
                for (int i = 0; i < n; i++) data[i] = rng.nextInt();

                long start = System.nanoTime();
                for (int val : data) heap.insert(val);
                tInsert += (System.nanoTime() - start);

                start = System.nanoTime();
                int prev = heap.extractMin();
                for (int i = 1; i < n; i++) {
                    int curr = heap.extractMin();
                    if (curr < prev) throw new IllegalStateException("Heap Property Violated!");
                    prev = curr;
                }
                tExtract += (System.nanoTime() - start);

                totalComps += heap.getTotalComparisons();
            }

            System.out.printf("N = %6d | Insert Time: %6.3f ms | Extract Time: %6.3f ms | Total Comps: %d\n",
                    n, (tInsert / (double) REPETITIONS) / 1e6, (tExtract / (double) REPETITIONS) / 1e6, totalComps / REPETITIONS);
        }
    }
}
