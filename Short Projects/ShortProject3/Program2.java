/**
 * A program to compare the RT of traditional Quick Sort algorithm
 * and the 2 pivot Multi Pivot Quick Sort algorithm by
 * Vladimir Yaroslavskiy, Jon Bentley, and Joshua Bloch.
 *
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * @version 1.0
 * @since 2016-02-24
 */

public class Program2 {

    /**
     * The traditional 1 pivot quick sort algorithm.
     * The RT observed are
     * Inputs       Runtime
     * 1 million    600ms
     * 10 million   5400ms
     *
     * @param arr  An input array which needs to be sorted
     * @param low  The first index of the array. Generally 0
     * @param high The last accessible element of the array. Generally arr.length-1
     * @param <T>  A type of element which extends the comparable class.
     */
    public static <T extends Comparable<? super T>> void quickSort(T[] arr, int low, int high) {
        if (low < high) {
            int pivot_location = partition(arr, low, high);
            quickSort(arr, low, pivot_location - 1);
            quickSort(arr, pivot_location + 1, high);
        }
    }

    /**
     * This method partitions the array by finding a random pivot and then all elements
     * on the left of pivot are smaller than or equal to the pivot, and all those on the
     * right of pivot are greater than pivot.
     *
     * @param arr An input array which needs to be partitioned
     * @param p   The starting index of the array which needs to be partitioned
     * @param r   The ending index of the array which needs to be partitioned
     * @param <T> A type of element which extends the comparable class.
     * @return It return the index of the array where partition is made.
     */
    private static <T extends Comparable<? super T>> int partition(T[] arr, int p, int r) {
        int pivot = randomWithRange(p, r);
        swap(arr, pivot, r);

        T X = arr[r];

        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (arr[j].compareTo(X) <= 0) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, r);
        return i + 1;

    }

    /**
     * The two pivot implementation of the Quick Sort Algorithm by Vladimir Yaroslavskiy, Jon Bentley, and Joshua Bloch.
     * The RT observed are
     * Inputs       Runtime
     * 1 million    700ms
     * 10 million   4500ms
     *
     * @param arr   An input array which needs to be sorted
     * @param left  The first index of the array. Generally 0.
     * @param right The last accessible element of the array. Generally arr.length-1
     * @param <T>   A type of element which extends the comparable class.
     */
    public static <T extends Comparable<? super T>> void twoPivotQuickSort(T[] arr, int left, int right) {

        if (left < right) {

            int pivot1 = randomWithRange(left, right);
            int pivot2 = randomWithRange(left, right);

            swap(arr, left, pivot1);
            swap(arr, right, pivot2);
            if (arr[left].compareTo(arr[right]) > 0)
                swap(arr, left, right);

            T X1 = arr[left];
            T X2 = arr[right];


            int l = left + 1;
            int k = left + 1;
            int g = right - 1;

            while (k <= g) {
                if (arr[k].compareTo(X1) < 0) {
                    swap(arr, k, l);
                    l++;
                } else if (arr[k].compareTo(X2) >= 0) {
                    while (arr[g].compareTo(X2) > 0 && k < g) {
                        g = g - 1;
                    }
                    swap(arr, k, g);
                    g = g - 1;
                    if (arr[k].compareTo(X1) < 0) {
                        swap(arr, k, l);
                        l++;
                    }
                }
                k = k + 1;
            }

            l = l - 1;
            g = g + 1;
            swap(arr, left, l);
            swap(arr, right, g);

            twoPivotQuickSort(arr, left, l - 1);

            twoPivotQuickSort(arr, l + 1, g - 1);

            twoPivotQuickSort(arr, g + 1, right);

        }
    }


    /**
     * This is the helper method which swaps values on the two indexes.
     *
     * @param arr An array of type T which elements needs to be swapped
     * @param i   Index of element 1
     * @param r   Index of element 2
     * @param <T>  A type of element which extends the comparable class.
     */
    private static <T extends Comparable<? super T>> void swap(T[] arr, int i, int r) {

        T temp = arr[i];
        arr[i] = arr[r];
        arr[r] = temp;
    }

    /**
     * A helper method which return a random number between the specified range
     *
     * @param min The lower bound of the range (inclusive)
     * @param max The upper bound of the range (inclusive)
     * @return A random number between [min....max]
     */
    static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    /**
     * Driver function to test the algorithms.
     * It generates an input size array from random
     * numbers and then passes them to function.
     *
     * @param args Unused.
     */
    public static void main(String[] args) {
        int testSize = 1000000;


        Integer[] arr = new Integer[testSize];

        //creating random numbers and putting them in array
        for (int i = 0; i < arr.length; i++) {
            arr[i] = randomWithRange(0, testSize * 10);
        }

        /**
         * Uncomment whichever algorithm you want to test.
         */


        //traditional quickSort
        long start1 = System.currentTimeMillis();
        quickSort(arr, 0, arr.length - 1);
        long end1 = System.currentTimeMillis();
        System.out.println(end1 - start1 + " ms");

        //2 pivot quickSort
//        long start = System.currentTimeMillis();
//        twoPivotQuickSort(arr, 0, arr.length - 1);
//        long end = System.currentTimeMillis();
//        System.out.println(end - start + " ms");

    }

}


/**
 * Output Recorded:
 * Number of Inputs         Traditional QuickSort           2 Pivot Quick Sort
 * 1 Million                600ms                           700ms
 * 10 Million               5400ms                          4500ms
 */