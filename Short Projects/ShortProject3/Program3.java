/**
 * A program to compare the RT of best version of merge sort
 * and that of quick sort.
 *
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * @version 1.0
 * @since 2016-02-24
 */
public class Program3 {

    /**
     * The best version of merge Sort algorithm
     * Various improvements are made from the normal merge sort
     * RT Observed
     * Input Number         Using insertion sort when array.length<=11
     * 1 million            600ms
     * 10 million           5000ms
     *
     * @param arr  An input array which needs to be sorted
     * @param low  The low index of the array
     * @param high The high index of the array
     * @param <T>  The generic Type which is implements the comparable interface
     */
    public static <T extends Comparable<? super T>> void mergeSort(T[] arr, int low, int high) {
        if (low >= high) return; //return without doing anything if only one element
        if (high - low <= 11) {
            insertionSort(arr, low, high);
            return;
        }
        int mid = (low + high) / 2; //split array into two halves
        mergeSort(arr, low, mid);
        mergeSort(arr, mid + 1, high);
        merge(arr, low, mid, high); //calling the merge method

    }

    /**
     * A method which is used to merge two array into one sorted array
     *
     * @param arr  The input array
     * @param low  low index of the array
     * @param mid  mid point of the array
     * @param high high index of the array
     * @param <T>  The generic Type which is implements the comparable interface
     */
    private static <T extends Comparable<? super T>> void merge(T[] arr, int low, int mid, int high) {
        Comparable[] aux = new Comparable[high - low + 1];
        int arr1ptr, arr1end, arr2ptr, arr2end;
        arr1ptr = low;
        arr1end = mid;
        arr2ptr = mid + 1;
        arr2end = high;
        int auxPtr = 0;
        while ((arr1ptr <= arr1end) && (arr2ptr <= arr2end)) { //loop till anyone of the arrays become empty.
            if (arr[arr1ptr].compareTo(arr[arr2ptr]) < 0) { //adding the smaller element to the temp list
                aux[auxPtr++] = arr[arr1ptr++];
            } else {
                aux[auxPtr++] = arr[arr2ptr++];
            }
        }
        //copying rest of the elements into temp list
        if (arr1ptr > arr1end) {
            for (int k = arr2ptr; k <= arr2end; k++) {
                aux[auxPtr++] = arr[k];
            }
        } else {
            for (int k = arr1ptr; k <= arr1end; k++) {
                aux[auxPtr++] = arr[k];
            }
        }
        int l = 0;
        //copy temp list back to main array
        for (int k = low; k <= high; k++)
            arr[k] = (T) aux[l++];


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
     * Insertion sort algorithm which will be used for array.length<=11
     *
     * @param arr  The input array which needs to be sorted
     * @param low  The low index of the array
     * @param high The high index of the array
     * @param <T>  The generic Type which is implements the comparable interface
     */
    public static <T extends Comparable<? super T>> void insertionSort(T[] arr, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            T temp = arr[i];
            int j;
            for (j = i - 1; j >= low && temp.compareTo(arr[j]) < 0; j--)
                arr[j + 1] = arr[j];
            arr[j + 1] = temp;
        }
    }


    /**
     * This is the helper method which swaps values on the two indexes.
     *
     * @param arr An array of type T which elements needs to be swapped
     * @param i   Index of element 1
     * @param r   Index of element 2
     * @param <T> A type of element which extends the comparable class.
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
     * A sample driver method to compare two algorithms
     *
     * @param args Unused
     */
    public static void main(String[] args) {
        int testSize = 1000000;
        Integer[] arr = new Integer[testSize];

        //creating random numbers and putting them in array
        for (int i = 0; i < arr.length; i++) {
            arr[i] = randomWithRange(0, testSize * 10);
        }
        long start = System.currentTimeMillis();
        /**
         * Comment either line to use that algorithm
         */
        mergeSort(arr, 0, arr.length - 1);
        //twoPivotQuickSort(arr, 0, arr.length - 1);

        long end = System.currentTimeMillis();
        System.out.println(end - start);

    }
}
/**
 * Observations:
 * Merge Sort is faster than quick sort
 * For an input value of 1M
 * Merge Sort: 600ms
 * Quick Sort: 800ms
 */