import java.util.Scanner;

/**
 * To find the permutations of given n digits using Heap's Algorithm
 * 
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * 
 * @version 1.0
 * @since 2016-04-11
 */

public class HeapsAlgorithm {

    /**
     * @param args the command line argument
     */
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the value of n : ");
        int n = sc.nextInt();
        int[] permutation = new int[n];
        for(int i=0;i<n;i++)
            permutation[i] = i+1;
        findPermutations(permutation,n);
    }

    /**
     * Function to find the permutations
     * 
     * @param permutation Current permutation
     * @param n number of digits whose position needs to be fixed
     */
    private static void findPermutations(int[] permutation, int n) {
        if(n==3){
            printPermutation(permutation);
            int p1 = permutation[0];
            int p2 = permutation[1];
            int p3 = permutation[2];
            swap(permutation, 0, 1, p2, p1);
            swap(permutation, 0, 2, p3, p2);
            swap(permutation, 0, 1, p1, p3);
            swap(permutation, 0, 2, p2, p1);
            swap(permutation, 0, 1, p3, p2);
        }
        else{
            for(int i=0;i<n-1;i++){
                findPermutations(permutation, n-1);
                if(n%2==0)
                    swap(permutation,i,n-1);
                else
                    swap(permutation,0,n-1);
            }
            findPermutations(permutation, n-1);
        }
    }

    /**
     * Helper function to swap two elements of an array
     * 
     * @param permutation Array whose elements needs to swapped
     * @param i Index 1
     * @param j Index 2
     */
    private static void swap(int[] permutation, int i, int j) {
        int temp = permutation[i];
        permutation[i] = permutation[j];
        permutation[j] = temp;
    }

    /**
     * Helper function to move two elements of an array
     * 
     * @param permutation Array whose elements needs to moved
     * @param i Index 1
     * @param j Index 2
     * @param one Element 1
     * @param two Element 2
     */
    private static void swap(int[] permutation,int i,int j,int one,int two){
        permutation[i] = one;
        permutation[j] = two;
        printPermutation(permutation);
    }
    /**
     * Helper function to print the current permutation
     * 
     * @param permutation Current Permutation
     */
    private static void printPermutation(int[] permutation) {
        for(int i=0;i<permutation.length;i++)
            System.out.print(permutation[i]+" ");
        System.out.println();
    }

}

/**
 * Sample Input:
 * 3
 * 
 * Sample Output:
 * 1 2 3
 * 2 1 3
 * 3 1 2
 * 1 3 2
 * 2 3 1
 * 3 2 1
 * 
 * Running Times (Without considering time taken for printing):
 * Input    TimeTaken(sec)
 *  8           1
 *  10          1
 *  12          2
 *  13          8
 *  14          130
 */