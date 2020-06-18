public class ShellSort {

    /*
    Shell sort is similar to Insertion sort, however instead of going one step back, we go h steps back.
    Shell sort is a fast algorithm based on insertion sort - insertion sort is slow for large unordered arrays since
    it only does adjacent exchanges.
    Shell sort allows exchanges of array elements over larger steps to produce partially sorted arrays that can
    then be efficiently sorted by insertion sorting algorithm.
    Recall: Insertion sort is fast for partially sorted arrays.

    h steps for h-sorting the array can have varied implementations.
    For this implementation h = 3h*1 as long as h < input.length/3
     */

    public static void sort(Comparable[] input) {
        int h = 1;
        while (h < input.length / 3) {
            h = (3 * h) + 1;
        }
        while (h >= 1) {
            for (int i = h; i < input.length; i++) {
                for (int j = i; j >= h; j -= h) {
                    if (SortingResource.less(input[j], input[j - h])) {
                        SortingResource.exchange(input, j, j - h);
                    }
                }
                h = h / 3;
            }
        }
    }

    public static void main(String[] args) {
        ShellSort.sort(SortingResource.SHELLSORT);
        for (String string : SortingResource.SHELLSORT) {
            System.out.print(string + " ");
        }
    }
}
