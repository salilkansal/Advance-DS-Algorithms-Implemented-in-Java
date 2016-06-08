import java.util.Scanner;

/**
 * A program to compare the RT of linear Fibonacci algorithm
 * and the log Fibonacci algorithm.
 *
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * @version 1.0
 * @since 2016-02-24
 */


public class Program1 {
    /**
     * Linear Fibonacci Algorithm
     * The RT observed are
     * Inputs       Runtime
     * 1 million    20ms
     * 10 million   200ms
     * 100 million  2000ms
     * 1 billion    20000ms
     *
     * @param n The number which fibonacci number is to be found
     * @param p The mod number to which we mod the result to prevent overflow
     * @return The fibonacci number at n modded with p
     */
    public static long linearFibonacci(long n, long p) {
        long pprev = 0;
        long prev = 1;
        if (n == 0)
            return pprev;
        if (n == 1)
            return prev;
        long i = 2;
        long num = pprev + prev;
        while (i < n) {
            pprev = prev;
            prev = num;
            num = (pprev + prev) % p;
            i++;
        }
        return num;
    }

    /**
     * Log Fibonacci Algorithm
     * The RT observed are
     * Inputs       Runtime
     * 1 million    0ms
     * 10 million   0ms
     * 100 million  0ms
     * 1 billion    0ms
     *
     * @param n The number which fibonacci number is to be found
     * @param p The mod number to which we mod the result to prevent overflow
     * @return The fibonacci number at n modded with p
     */
    public static long logFibonacci(long n, long p) {
        if (n == 0)
            return 0;
        if (n == 1)
            return 1;
        long[][] A = {{1, 1}, {1, 0}};

        return power(A, n - 1, p)[0][0];
    }

    /**
     * A power function to calculate a power of a matrix
     *
     * @param a     The matrix of which power is to be calculated
     * @param power The power to which the matrix is to be raised
     * @param p     The mod value which is used to prevent overflow
     * @return A matrix which is a^power
     */
    private static long[][] power(long[][] a, long power, long p) {
        if (power == 0)
            return a;
        if (power == 1)
            return a;
        else {
            long[][] half = power(a, power / 2, p);
            long[][] result = multiplyMatrix(half, half, p);
            if (power % 2 == 0)
                return result;
            else
                return multiplyMatrix(result, a, p);
        }
    }

    /**
     * A helper method which multiplies two matrices of 2x2 size
     *
     * @param a1 Matrix a
     * @param a2 Matrix b
     * @param p  The mod value which is used to prevent overflow
     * @return A matrix which is product of a and b
     */
    static long[][] multiplyMatrix(long[][] a1, long[][] a2, long p) {
        long[][] result = new long[2][2];

        result[0][0] = ((a1[0][0] * a2[0][0]) + (a1[0][1] * a2[1][0])) % p;
        result[0][1] = ((a1[0][0] * a2[1][0]) + (a1[0][1] * a2[1][1])) % p;
        result[1][0] = ((a1[1][0] * a2[0][0]) + (a1[1][1] * a2[1][0])) % p;
        result[1][1] = ((a1[1][0] * a2[0][1]) + (a1[1][1] * a2[1][1])) % p;

        return result;
    }

    /**
     * A driver method to test the two algorithms
     * Comment either one block to test that algorithm
     *
     * @param args Unused.
     */
    public static void main(String args[]) {


//        System.out.println("Enter a number");
//        Scanner s = new Scanner(System.in);
//        long p = 999953;
//        long n = s.nextLong();
//        long start = System.currentTimeMillis();
//        long result1 = logFibonacci(n, p);
//        long end = System.currentTimeMillis();
//        System.out.println(result1);
//
//        System.out.println(end - start + " ms");
//


        System.out.println("Enter a number");
        Scanner s = new Scanner(System.in);
        long p = 999953;
        long n = s.nextLong();
        long start = System.currentTimeMillis();
        long result1 = logFibonacci(n, p);
        long end = System.currentTimeMillis();
        System.out.println(result1);
        System.out.println(end - start + " ms");


    }
}
/**
 * Run times i got were as follows:
 * <p>
 * input                            linear(ms)                          log(ms)
 * <p>
 * 1000                             0                                      0
 * <p>
 * 1 million                        20                                     0
 * <p>
 * 10 million                       200                                    0
 * <p>
 * 100 million                      2000                                   0
 * <p>
 * 1 billion                        20000                                  0
 */