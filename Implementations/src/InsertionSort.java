public class InsertionSort {

    public static void sort(Comparable[] input) {
        for (int i = 1; i < input.length; i++) {
            int elementIndex = i;
            for (int j = i - 1; j >= 0; j--, elementIndex--) {
                if (SortingResource.less(input[elementIndex], input[j])) {
                    SortingResource.exchange(input, j, elementIndex);
                }
            }
        }
    }

    public static void main(String[] args) {
        InsertionSort.sort(SortingResource.STRING);
        for (String string : SortingResource.STRING) {
            System.out.print(string + " ");
        }
        System.out.println();
        InsertionSort.sort(SortingResource.PRIMES);
        for (Integer integer : SortingResource.PRIMES) {
            System.out.print(integer + " ");
        }
    }
}
