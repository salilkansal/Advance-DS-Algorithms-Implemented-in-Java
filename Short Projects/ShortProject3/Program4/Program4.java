
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Selection Problem
 * To find K largest elements in an unsorted array using random pivot
 *
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 */

public class Program4 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in;
        if (args.length > 0)
            in = new Scanner(new File(args[0]));
        else
            in = new Scanner(new File("KLargestInputs.txt"));
        int n = in.nextInt();           // Number of elements in the input
        Integer[] input = new Integer[n];
        for (int i = 0; i < n; i++)
            input[i] = in.nextInt();
        int k = in.nextInt();           // Number of largest values that needs to be returned
        if (n < k)                         // If the values that needs to be returned are less than the total number of values
            System.out.println("Input size is less than k");
        else {
            Long start = System.currentTimeMillis();
            int q = select(input, 0, n - 1, k);
            Long end = System.currentTimeMillis();
            System.out.println("The K largest Elements are : ");
            for (int i = q; i < n; i++)
                System.out.print(input[i] + " ");
            System.out.println();
            System.out.println("Execution time in ms : " + (end - start));
        }
    }

    // Recursive function that returns start position of our desired elements in the array
    public static <T extends Comparable<? super T>> int select(T[] arr, int p, int r, int k) {
        if (r - p + 1 == k)                          // If the input size is equal to k we just return the start index
            return p;
        //Generating a random index between array start and array end
        int pivotIndex = p + (int) (Math.random() * ((r - p) + 1));
        swap(arr, pivotIndex, r);                 // Placing the pivot in the last position
        // Elements greater than pivot will be placed at end of the array.
        // pivotIndex is used to keep track of the poisiton where they are supposed to be inserted
        pivotIndex = r - 1;
        for (int i = p; i <= pivotIndex; ) {
            if (arr[i].compareTo(arr[r]) >= 0) {    // If the element is greater than pivot place it at the pivotIndex
                swap(arr, i, pivotIndex);
                pivotIndex--;
            } else
                i++;
            // We also start comparing from right end of the array to reduce the time
            while (i <= pivotIndex && arr[pivotIndex].compareTo(arr[r]) >= 0)
                pivotIndex--;
        }
        ++pivotIndex;
        swap(arr, pivotIndex, r);
        // If there are k elements to right of pivot then our result will be starting after pivotIndex
        // If there are more than k elements to right then all the desired elements will be to the right
        // If there are less than k elements then all the elements to right will be in the result
        // and k-(number of elements present to the right of pivot) values will be in the left side
        if (r - pivotIndex > k)
            return select(arr, pivotIndex + 1, r, k);
        else if (r - pivotIndex < k)
            return select(arr, p, pivotIndex, k - (r - pivotIndex));
        return pivotIndex + 1;
    }

    // Function to swap two elements in the array given their index positions
    private static <T extends Comparable<? super T>> void swap(T[] arr, int index1, int index2) {
        T temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

}

/**
 * Program Report in Report.docx
 */
