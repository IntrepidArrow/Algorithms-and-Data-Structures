public class SortingResource {

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