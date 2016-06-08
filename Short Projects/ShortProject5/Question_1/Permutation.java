import java.util.Scanner;

/**
 * To find the permutations of given n digits
 * 
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * 
 * @version 1.0
 * @since 2016-04-11
 */

public class Permutation {

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
        if(n==0)
            printPermutation(permutation);
        else{
            for(int i=0;i<n;i++){
                swap(permutation,i,n-1);
                findPermutations(permutation, n-1);
                swap(permutation,i,n-1);
            }
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
 * 2 3 1
 * 3 2 1
 * 3 1 2
 * 1 3 2
 * 2 1 3
 * 1 2 3
 * 
 * Running Times (Without considering time taken for printing):
 * Input    TimeTaken(sec)
 *  8           2
 *  10          3
 *  12          7
 *  13          69
 *  14          826
 */