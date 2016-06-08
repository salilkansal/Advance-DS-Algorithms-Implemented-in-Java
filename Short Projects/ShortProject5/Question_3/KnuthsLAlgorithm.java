import java.util.Scanner;

/**
 * Implement Knuth's algorithm for generating permutations in lexicographic order.
 * 
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * 
 * @version 1.0
 * @since 2016-04-11
 */

public class KnuthsLAlgorithm {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number : ");
        String number = sc.next();
        while(true){
            String nextNumber = findNext(number);
            if(nextNumber.equals(number))
                break;
            System.out.println(nextNumber);
            number = nextNumber;
        }
    }

    /**
     * Function to find the next biggest number in lexicographical order
     * 
     * @param number given number
     * 
     * @return Next biggest number
     */
    private static String findNext(String number) {
        int length = number.length();
        int[] input = new int[length];
        for(int i=0;i<length;i++)
            input[i] = Character.getNumericValue(number.charAt(i));
        // Find maximum index 'j' such that input[j]<input[j+1]
        int j = -1;
        for(int i=length-2;i>=0;i--){
            if(input[i]<input[i+1]){
                j = i;
                break;
            }
        }
        // If there is no such element then the given number is the biggest
        if(j==-1)
            return number;
        // Find maximum index 'l' such that input[j]<input[l]
        int l = j+1;
        for(int i=length-1;i>j+1;i--){
            if(input[j]<input[i]){
                l = i;
                break;
            }
        }
        swap(input,l,j);
        // Reverse the elements after j+1
        j++;
        l = length-1;
        while(j<l){
            swap(input,j,l);
            j++;
            l--;
        }
        StringBuilder nextNumber = new StringBuilder();
        for(int i=0;i<length;i++)
            nextNumber.append(input[i]);
        return nextNumber.toString();
    }

    /**
     * Helper function to swap two elements in the given array
     * 
     * @param input input array
     * @param index1 Index 1
     * @param index2 Index 2
     */
    private static void swap(int[] input,int index1,int index2){
        int temp = input[index1];
        input[index1] = input[index2];
        input[index2] = temp;
    }

}

/**
 * Sample Input:
 * 1234
 * 
 * Sample Output:
 * 1243
 * 1324
 * 1342
 * 1423
 * 1432
 * 2134
 * 2143
 * 2314
 * 2341
 * 2413
 * 2431
 * 3124
 * 3142
 * 3214
 * 3241
 * 3412
 * 3421
 * 4123
 * 4132
 * 4213
 * 4231
 * 4312
 * 4321
 */