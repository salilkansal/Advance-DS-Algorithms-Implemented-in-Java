import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * ShuntingYard class to calculate arithmetic expressions.
 * It converts the infix to polish notation and then it calculates
 * that expression by use of stack
 *
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * @version 1.0
 * @since 2016-02-28
 */
class ShuntingYard {

    // Associativity constants for operators
    private static final int LEFT_ASSOC = 0;
    private static final int RIGHT_ASSOC = 1;

    private static final Map<String, int[]> OPERATORS = new HashMap<>();

    static {
        // Map<"token", []{precendence, associativity}>
        OPERATORS.put("+", new int[]{0, LEFT_ASSOC});
        OPERATORS.put("-", new int[]{0, LEFT_ASSOC});
        OPERATORS.put("*", new int[]{1, LEFT_ASSOC});
        OPERATORS.put("/", new int[]{1, LEFT_ASSOC});
        OPERATORS.put("%", new int[]{1, LEFT_ASSOC});
        OPERATORS.put("^", new int[]{2, RIGHT_ASSOC});
        OPERATORS.put("!", new int[]{3, LEFT_ASSOC});
        OPERATORS.put("~", new int[]{3, LEFT_ASSOC});
    }

    /**
     * Test if a certain is an operator .
     *
     * @param token The token to be tested .
     * @return True if token is an operator . Otherwise False .
     */
    private static boolean isOperator(String token) {
        return OPERATORS.containsKey(token);
    }

    public static boolean isNumeric(String str) {
        return str.matches("\\d+");  //match a number with optional '-' and decimal.
    }

    /**
     * Test the associativity of a certain operator token .
     *
     * @param token The token to be tested (needs to operator).
     * @param type  LEFT_ASSOC or RIGHT_ASSOC
     * @return True if the tokenType equals the input parameter type .
     */
    private static boolean isAssociative(String token, int type) {
        if (!isOperator(token)) {
            throw new IllegalArgumentException("Invalid token: " + token);
        }
        if (OPERATORS.get(token)[1] == type) {
            return true;
        }
        return false;
    }

    /**
     * Compare precendece of two operators.
     *
     * @param token1 The first operator .
     * @param token2 The second operator .
     * @return A negative number if token1 has a smaller precedence than token2,
     * 0 if the precendences of the two tokens are equal, a positive number
     * otherwise.
     */
    private static int cmpPrecedence(String token1, String token2) {
        if (!isOperator(token1) || !isOperator(token2)) {
            throw new IllegalArgumentException("Invalid tokens: " + token1 + " " + token2);
        }
        return OPERATORS.get(token1)[0] - OPERATORS.get(token2)[0];
    }

    public static String[] infixToRPN(String[] inputTokens) {
        ArrayList<String> out = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        // For all the input tokens [S1] read the next token [S2]
        for (String token : inputTokens) {
            if (isOperator(token)) {
                // If token is an operator (x) [S3]
                while (!stack.empty() && isOperator(stack.peek())) {
                    // [S4]
                    if ((isAssociative(token, LEFT_ASSOC) && cmpPrecedence(
                            token, stack.peek()) <= 0)
                            || (isAssociative(token, RIGHT_ASSOC) && cmpPrecedence(
                            token, stack.peek()) < 0)) {
                        out.add(stack.pop());    // [S5] [S6]
                        continue;
                    }
                    break;
                }
                // Push the new operator on the stack [S7]
                stack.push(token);
            } else if (token.equals("(")) {
                stack.push(token);    // [S8]
            } else if (token.equals(")")) {
                // [S9]
                while (!stack.empty() && !stack.peek().equals("(")) {
                    out.add(stack.pop()); // [S10]
                }
                stack.pop(); // [S11]
            } else {
                out.add(token); // [S12]
            }
        }
        while (!stack.empty()) {
            out.add(stack.pop()); // [S13]
        }
        String[] output = new String[out.size()];
        return out.toArray(output);
    }

    public static BigNumber evaluateExpression(HashMap<Character, BigNumber> variables, String expression) {
        String[] input = expression.split("(?<=[-+*/~!%^])|(?=[-+*/~!%^])");
        String[] polish = infixToRPN(input);
        Stack<BigNumber> temp = new Stack<>();
        for (String curr : polish) {
            if (isOperator(curr)) {
                BigNumber num1;
                BigNumber num2;
                switch (curr) {
                    case "+":
                        num2 = temp.pop();
                        num1 = temp.pop();
                        temp.push(BigNumber.add(num1, num2));
                        break;
                    case "-":
                        num2 = temp.pop();
                        num1 = temp.pop();
                        temp.push(BigNumber.subtract(num1, num2));
                        break;
                    case "*":
                        num2 = temp.pop();
                        num1 = temp.pop();
                        temp.push(BigNumber.product(num1, num2));
                        break;
                    case "/":
                        num2 = temp.pop();
                        num1 = temp.pop();
                        temp.push(BigNumber.divide(num1, num2));
                        break;
                    case "%":
                        num2 = temp.pop();
                        num1 = temp.pop();
                        temp.push(BigNumber.mod(num1, num2));
                        break;
                    case "^":
                        num2 = temp.pop();
                        num1 = temp.pop();
                        temp.push(BigNumber.power(num1, num2));
                        break;
                    case "!":
                        num1 = temp.pop();
                        temp.push(BigNumber.factorial(num1));
                        break;
                    case "~":
                        num1 = temp.pop();
                        temp.push(BigNumber.squareRoot(num1));
                        break;
                }
            } else if (isNumeric(curr)) {
                temp.push(new BigNumber(curr));
            } else {
                temp.push(variables.get(curr.charAt(0)));
            }
        }
        return temp.pop();
    }

}