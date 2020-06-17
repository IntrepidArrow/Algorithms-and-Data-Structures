public class SelectionSort {

    /*
    Selection sort is an elementary sorting algorithm.
    The algorithm partitions the input array into two subarrays of sorted and unsorted elements where the sorted
    subarray in in the beginning of the input array. In each iteration of the algorithm, it first finds the smallest
    element in the unsorted array and then exchanges it into the correct index position in the sorted subarray.
     */

    public static void sort(Comparable[] input) {
        for (int i = 0; i < input.length; i++) {
            int min = i;
            // find index of minimum element in unsorted subarray
            for (int j = i + 1; j < input.length; j++) {
                if (SortingResource.less(input[j], input[min])) {
                    min = j;
                }
            }
            // swap minimum element into correct position in sorted subarray
            SortingResource.exchange(input, i, min);
        }
    }

    public static void main(String[] args) {
        Integer[] primes = {3, 7, 2, 5, 17, 19, 13, 11};
        for (Integer integer : primes) {
            System.out.print(integer + " ");
        }
        System.out.println();

        SelectionSort.sort(primes);
        for (Integer integer : primes) {
            System.out.print(integer + " ");
        }
    }
}
