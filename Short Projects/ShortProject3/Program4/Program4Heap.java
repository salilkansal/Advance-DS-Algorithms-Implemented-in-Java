import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Selection Problem
 * To find K largest elements in an unsorted array using Binary Heap implementation
 *
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 */
public class Program4Heap {
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
        Integer k = in.nextInt();           // Number of largest values that needs to be returned
        if (n < k)                         // If the values that needs to be returned are less than the total number of values
            System.out.println("Input size is less than k");
        else {
            // Build a minHeap that holds exactly k largest elements at any point of time
            BinaryHeap<Integer> pq = new BinaryHeap<>(k + 1, (Integer o1, Integer o2) -> {
                return o1 - o2;
            });
            Long start = System.currentTimeMillis();
            for (int i = 0; i < n; i++) {
                // If there are less than k elements in the heap we can directly add the element into the heap.
                if (pq.size < k)
                    pq.add(input[i]);
                else {
                    // If the heap is filled compare the minimum element present in the heap with current element
                    // As it is a min heap, the minimum element will always be present at the top.
                    // If the min element is smaller than the current element we remove the minimum element
                    // and replace it with current element.
                    if (pq.peek() < input[i])
                        pq.setTop(input[i]);
                }
            }
            System.out.println("The K largest Elements are : ");
            for (int i = 0; i < k; i++)
                System.out.print(pq.remove() + " ");
            Long end = System.currentTimeMillis();
            System.out.println();
            System.out.println("Execution time in ms : " + (end - start));
        }
    }
}

/**
 * Program Report in Report.docx
 */