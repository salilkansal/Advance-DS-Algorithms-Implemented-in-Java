import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * A program which reads a txt input file and parses the expressions based on thhe precedence.
 * Pass the file as arg[0]
 * (optional) Pass base as arg[1]
 * Default base is 10
 *
 * usage: LP1ioDriver.java <filename.txt> <optional base between 2 and 10000>
 *
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * @version 1.0
 * @since 2016-02-28
 */

public class LP1ioDriver {

    /**
     * A function to read instructions / expressions and store it in an array
     *
     * @param s Scanner
     * @return Array of Strings where each string is an expression
     */
    public static String[] readInput(Scanner s) {
        // Assuming maximum number of instructions to be no more than 1000
        String[] inputs = new String[1000];
        // Expressions are stored as strings with line number as their index
        while (s.hasNext()) {
            int i = s.nextInt();
            inputs[i] = s.next();
        }
        // Finding the maximum line number of all the expressions
        int num = 1000;
        for (int i = 999; i > 0; i--) {
            if (inputs[i] == null)
                num--;
            else
                break;
        }
        return Arrays.copyOfRange(inputs, 0, num);
    }

    /**
     * A function which parses input lines and performs the required operations
     *
     * @param s1 Scanner object to read input
     */
    public static void calcInput(Scanner s1) {
        String[] inputs = readInput(s1);        // Read the input
        HashMap<Character, BigNumber> variables = new HashMap<>();
        int i = 0;
        while (i < inputs.length) {
            String CurrInstr = inputs[i];
            if (CurrInstr != null) {
                // First position of an expression will always contain a variable
                Character var1 = CurrInstr.charAt(0);
                // If we did not come across the variable previously we store it in the map
                if (!variables.containsKey(var1))
                    variables.put(var1, null);
                // Checking if its an expression or just a variable
                if (CurrInstr.length() > 1) {
                    // The operator will be at the second position
                    char op = CurrInstr.charAt(1);
                    switch (op) {
                        case '=':
                            String assignment = CurrInstr.substring(2);
                            variables.put(var1, ShuntingYard.evaluateExpression(variables, assignment));
                            break;
                        case '?':
                            String condition = CurrInstr.substring(2);
                            String[] ifElse = condition.split(":");
                            if (!variables.get(var1).isZero()) {
                                i = Integer.parseInt(ifElse[0]);
                                continue;
                            } else {
                                if (ifElse.length > 1) {
                                    i = Integer.parseInt(ifElse[1]);
                                    continue;
                                }
                            }
                            break;
                        case ')':
                            variables.get(var1).printList();
                            break;
                    }
                } else
                    System.out.println(variables.get(var1));
            }
            i++;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s1;
        if (args.length > 0)
            s1 = new Scanner(new File(args[0]));
        else
            s1 = new Scanner(System.in);
        if (args.length > 1)
            BigNumber.base = Integer.parseInt(args[1]);
        calcInput(s1);
    }

}