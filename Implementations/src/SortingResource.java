public class SortingResource {

    public static final String[] STRING = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
    public static final Integer[] PRIMES = {3, 7, 2, 5, 17, 19, 13, 11, 23};

    public static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    // Basic swap method that swaps elements in positions i and j in array
    public static void exchange(Comparable[] array, int i, int j) {
        Comparable temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static boolean isSorted(Comparable[] array) {
        for (int i = 1; i < array.length; i++) {
            if (less(array[i], array[i - 1])) {
                return false;
            }
        }
        return true;
    }
}
