public class MergeSort {

    /*
    Mergesort is java sorting algorithm for objects.
    Basic idea is to divide the array into 2 halves, recursively sort each half and then merge the halves
    Merge sort is an efficient algorithm and can be used on large input arrays.
    Worst-time complexity of MergeSort is O(NlgN)
     */

    public static void sort(Comparable[] input) {
        Comparable[] aux = new Comparable[input.length];
        sort(input, aux, 0, input.length - 1);
    }

    // Want to merge two sorted halves such that the resulting array is also sorted
    private static void merge(Comparable[] input, Comparable[] aux, int low, int mid, int high) {
        assert SortingResource.isSorted(input, low, mid);
        assert SortingResource.isSorted(input, mid + 1, high);

        // Make a copy of the input array in an auxiliary array from which elements will be chosen
        for (int i = low; i <= high; i++) {
            aux[i] = input[i];
        }

        // Merge
        int a = low;
        int b = mid + 1;
        for (int index = low; index <= high; index++) {
            if (a > mid) {
                input[index] = aux[b++];
            } else if (b > high) {
                input[index] = aux[a++];
            } else if (SortingResource.less(aux[a], aux[b])) {
                input[index] = aux[a++];
            } else {
                input[index] = aux[b++];
            }
        }

        // Check to see if result array is sorted
        assert SortingResource.isSorted(input, low, high);
    }

    private static void sort(Comparable[] input, Comparable[] aux, int low, int high) {

        if (high <= low) {
            return;
        }
        int mid = (low + high) / 2;
        sort(input, aux, low, mid); // Left half
        sort(input, aux, mid + 1, high);    // Right half
        merge(input, aux, low, mid, high);

    }

    public static void main(String[] args) {

        for (String string : SortingResource.STRING) {
            System.out.print(string + " ");
        }
        System.out.println();

        MergeSort.sort(SortingResource.STRING);
        for (String string : SortingResource.STRING) {
            System.out.print(string + " ");
        }
        System.out.println();

        for (Integer integer : SortingResource.PRIMES) {
            System.out.print(integer + " ");
        }
        System.out.println();

        MergeSort.sort(SortingResource.PRIMES);
        for (Integer integer : SortingResource.PRIMES) {
            System.out.print(integer + " ");
        }
    }
}
