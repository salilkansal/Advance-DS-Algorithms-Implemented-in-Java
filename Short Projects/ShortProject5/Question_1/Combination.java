import java.util.Scanner;

/**
 * To find the k combinations of given n digits
 * 
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * 
 * @version 1.0
 * @since 2016-04-11
 */

public class Combination {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the value of n : ");
        int n = sc.nextInt();
        System.out.print("Enter the value of k : ");
        int k = sc.nextInt();
        boolean[] consider = new boolean[n];
        findCombinations(n,k,consider);
    }

    /**
     * Function to find the combinations
     * 
     * @param n Digit to be considered
     * @param k Remaining digits to be considered
     * @param consider Contains digits considered so far
     */
    private static void findCombinations(int n, int k, boolean[] consider) {
        // If number of digits to be considered is greater
        // than the remaining number of digits then the
        // combination is not possible
        if(k>n) {

        }
        // If the k digits are selected then print the combination
        else if(k==0)
            printCombination(consider);
        else {
            // Recursive call to the function
            // not considering the current digit
            findCombinations(n-1, k, consider);
            consider[n-1] = true;
            // Recursive call to the function
            // considering the current digit
            findCombinations(n-1, k-1, consider);
            consider[n-1] = false;
        }
    }

    /**
     * Helper function to print the current combination
     * 
     * @param consider Boolean array which contains true for
     *                 the digits considered in the combination
     */
    private static void printCombination(boolean[] consider) {
        for(int i=0;i<consider.length;i++){
            if(consider[i])
                System.out.print(i+1+" ");
        }
        System.out.println();
    }

}

/**
 * Sample Input:
 * 4 2
 * 
 * Sample Output:
 * 
 */