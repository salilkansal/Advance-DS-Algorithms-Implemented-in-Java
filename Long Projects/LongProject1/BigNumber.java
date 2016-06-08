import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/**
 * BigNumber class to store very large numbers.
 * It uses arraylist as the underlying data structure
 * It has many methods
 * add, subtract, product, divide, power, factorial, square root, mod
 * The default base is 10
 * It works on any base
 * It also works for negative numbers
 *
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * @version 1.0
 * @since 2016-02-28
 */
public class BigNumber implements Iterable<Long>, Comparable<BigNumber> {
    static int base = 10;
    List<Long> magnitude;
    boolean negative = false;

    public BigNumber() {
        magnitude = new ArrayList<>();
    }

    /**
     * Constructor to make a BigNumber object from a string
     *
     * @param s input string of number, has only digits
     */
    public BigNumber(String s) {
        magnitude = new ArrayList<>();
        if (s.charAt(0) == '-')
            negative = true;
        if (base == 10) {
            int zero = 0;
            if (s.charAt(0) == '-')
                zero = 1;
            for (int i = s.length() - 1; i >= zero; i--)
                magnitude.add(Long.parseLong(String.valueOf(s.charAt(i))));
        } else
            magnitude = Convert(10, base, s);
    }

    /**
     * Constructor to make a BigNumber object from a number
     *
     * @param num input number
     */
    public BigNumber(int num) {
        magnitude = new ArrayList<>();
        if (num == 0)
            magnitude.add((long) 0);
        else if (num < 0) {
            negative = true;
            num *= -1;
        }
        while (num > 0) {
            long rem = num % base;
            magnitude.add(rem);
            num /= base;
        }
    }

    /**
     * Constructor to make a BigNumber object from a number
     *
     * @param num input number
     */
    public BigNumber(Long num) {
        magnitude = new ArrayList<>();
        if (num == 0)
            magnitude.add((long) 0);
        else if (num < 0) {
            negative = true;
            num *= -1;
        }
        while (num > 0) {
            long rem = num % base;
            magnitude.add(rem);
            num /= base;
        }
    }

    /**
     * Converts the number from one base to another
     *
     * @param from original base
     * @param to   target base
     * @param s    String containing the number in from base
     * @return A list of digits of the number in target base
     */
    public static List<Long> Convert(int from, int to, String s) {
        //Return error if input is empty
        if (s == null || s.isEmpty()) {
            return null;
        }

        //convert string to an array of integer digits representing number in base:from
        int il = s.length();
        int[] fs = new int[il];
        int k = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            fs[k++] = Integer.parseInt(String.valueOf(s.charAt(i)));
        }

        //find how many digits the output needs
        int ol = il * (from / to + 1);
        int[] ts = new int[ol + 10]; //assign accumulation array
        Long[] cums = new Long[ol + 10]; //assign the result array
        Arrays.fill(cums, (long) 0);
        ts[0] = 1; //initialize array with number 1

        //evaluate the output
        for (int i = 0; i < il; i++) //for each input digit
        {
            for (int j = 0; j < ol; j++) //add the input digit times (base:to from^i) to the output accumulator
            {
                cums[j] += ts[j] * fs[i];
                long temp = cums[j];
                long rem;
                int ip = j;
                do // fix up any remainders in base:to
                {
                    rem = temp / to;
                    cums[ip] = temp - rem * to;
                    ip++;
                    cums[ip] += rem;
                    temp = cums[ip];
                }
                while (temp >= to);
            }

            //calculate the next power from^i) in base:to format
            for (int j = 0; j < ol; j++) {
                ts[j] = ts[j] * from;
            }
            for (int j = 0; j < ol; j++) //check for any remainders
            {
                int temp = ts[j];
                int rem;
                int ip = j;
                do  //fix up any remainders
                {
                    rem = temp / to;
                    ts[ip] = temp - rem * to;
                    ip++;
                    ts[ip] += rem;
                    temp = ts[ip];
                }
                while (temp >= to);
            }
        }
        ArrayList<Long> arr = new ArrayList<>(Arrays.asList(cums));
        int num = arr.size();
        for (int i = arr.size() - 1; i > 0; i--) {
            if (arr.get(i) == 0)
                num--;
            else break;
        }
        return arr.subList(0, num);
    }

    /**
     * Compares two biginteger objects by subtracting them
     *
     * @param o Another object which needs to be compared
     * @return +ve if this is greater
     * 0 if equal
     * -ve if o is greater
     */
    @Override
    public int compareTo(BigNumber o) {
        if (this.negative != o.negative) {
            if (this.negative)
                return -1;
            else
                return 1;
        }
        String s1 = this.toString();
        String s2 = o.toString();
        if (s1.length() < s2.length())
            return -1;
        else if (s1.length() > s2.length())
            return 1;
        else {
            for (int i = 0; i < s1.length(); i++) {
                if (s1.charAt(i) != s2.charAt(i)) {
                    int x = Integer.parseInt(String.valueOf(s1.charAt(i)));
                    int y = Integer.parseInt(String.valueOf(s2.charAt(i)));
                    if (x > y)
                        return 1;
                    else
                        return -1;
                }
            }
            return 0;
        }
    }


    /**
     * A method which converts the BigNumber object to a string of base 10.
     * String will always be in base 10
     *
     * @return A string of number in base 10
     */
    @Override
    public String toString() {
        if (base != 10) {
            int temp = base;
            base = 10;
            BigNumber sum = new BigNumber(0);
            for (int i = 0; i < magnitude.size(); i++) {
                BigNumber baseMultiply = new BigNumber((long) (magnitude.get(i) * Math.pow(temp, i)));
                sum = add(sum, baseMultiply);
            }
            String result = sum.toString();
            base = temp;
            return result;
        }
        StringBuilder sb = new StringBuilder();
        magnitude.forEach(sb::append);
        int i = 0;
        if (negative) {
            sb.append('-');
            i = 1;
        }
        sb.reverse();
        while (i < sb.length()) {
            if (sb.charAt(i) != '0')
                break;
            sb.deleteCharAt(i);
        }
        return sb.toString();
    }

    /**
     * Function to print the contents of the list preceded by the base
     */
    void printList() {
        System.out.print(base + ": ");
        for (Long u : magnitude) System.out.print(u + " ");
        System.out.println();
    }

    @Override
    public Iterator<Long> iterator() {
        return magnitude.iterator();
    }

    /**
     * Function to toggle the sign of number
     */
    void toggle() {
        this.negative = !this.negative;
    }

    /**
     * Function to check if the number is zero or not
     *
     * @return True: The number is zero
     * False: The number is non zero
     */
    boolean isZero() {
        for (Long num : magnitude) {
            if (num != 0)
                return false;
        }
        return true;
    }

    /**
     * Function to add two big numbers
     *
     * @param a First Big Number
     * @param b Second Big Number
     * @return sum of first and second Big Numbers
     * (FirstBigNumber + SecondBigNumber)
     */
    static BigNumber add(BigNumber a, BigNumber b) {
        long carry = 0;                     //default carry value is 0
        Iterator<Long> l1 = a.iterator();
        Iterator<Long> l2 = b.iterator();
        BigNumber addition = new BigNumber();
        if (a.negative != b.negative) {
            if (a.negative) {               // If a is negative and b is positive
                a.negative = false;
                addition = subtract(b, a);
                a.negative = true;
                return addition;
            } else {                        // If b is negative and a is positive
                b.negative = false;
                addition = subtract(a, b);
                b.negative = true;
                return addition;
            }
        } else if (a.negative)              // If both a and b are negative
            addition.negative = true;
        while (l1.hasNext() && l2.hasNext()) {
            long x = l1.next();
            long y = l2.next();
            // The remainder goes as sum
            addition.magnitude.add((carry + x + y) % base);
            // Quotient becomes the new carry
            carry = (carry + x + y) / base;
        }
        while (l1.hasNext()) {              //if list2 ends then just add carry to list1 elements
            long x = l1.next();
            addition.magnitude.add((carry + x) % base);
            carry = (carry + x) / base;
        }
        while (l2.hasNext()) {              //if list1 ends then just add carry to list2 elements
            long y = l2.next();
            addition.magnitude.add((carry + y) % base);
            carry = (carry + y) / base;
        }
        if (carry != 0)                     //If the MSD results in overflow
            addition.magnitude.add(carry);
        return addition;
    }

    /**
     * Function to subtract two big numbers
     *
     * @param a First Big Number
     * @param b Second Big Number
     * @return difference of first and second Big Numbers
     * (FirstBigNumber - SecondBigNumber)
     */
    static BigNumber subtract(BigNumber a, BigNumber b) {
        int borrow = 0;                         //default borrow value is 0
        Iterator<Long> l1, l2;
        BigNumber subtraction = new BigNumber();
        if (a.negative != b.negative) {
            if (a.negative) {               // If a is negative and b is positive
                b.negative = true;
                subtraction = add(a, b);
                b.negative = false;
            } else {                        // If b is negative and a is positive
                b.negative = false;
                subtraction = add(a, b);
                b.negative = true;
            }
            return subtraction;
        } else {
            if (a.negative) {               // If both a and b are negative
                a.negative = false;
                b.negative = false;
                subtraction = subtract(b, a);
                a.negative = true;
                b.negative = true;
                return subtraction;
            }
        }
        // Comparing both the integers and taking the largest value in l1 and smallest in l2
        int res = a.compareTo(b);
        if (res > 0) {
            l1 = a.iterator();
            l2 = b.iterator();
        } else if (res < 0) {
            l2 = a.iterator();
            l1 = b.iterator();
            subtraction.negative = true;
        } else                              // If they are equal return zero
            return new BigNumber("0");
        while (l2.hasNext() && l1.hasNext()) {
            long x = l1.next();
            long y = l2.next();
            if (x != 0) {
                x = x - borrow;             //decrement the borrow value
                if (x < y)
                    borrow = 1;             //take borrow if upper number is smaller
                else
                    borrow = 0;
                //add the borrow if needed from higher significant digit
                x = borrow * base + x;
            } else {
                if (borrow == 1)
                    x = base - 1;
                else {
                    if (y != 0) {
                        borrow = 1;
                        x = base;
                    }
                }
            }
            subtraction.magnitude.add(x - y);
        }
        //if list1 has elements left then keep adding them after considering the borrow value
        while (l1.hasNext()) {
            long x = l1.next();
            if (x != 0) {
                x = x - borrow;
                borrow = 0;
            } else {
                if (borrow == 1)
                    x = base - 1;
            }
            if (!l1.hasNext() && x == 0)
                break;
            subtraction.magnitude.add(x);
        }
        return subtraction;
    }

    /**
     * Function to multiply two big numbers
     *
     * @param a First Big Number
     * @param b Second Big Number
     * @return product of first and second Big Numbers
     * (FristBigNumber * SecondBigNumber)
     */
    static BigNumber product(BigNumber a, BigNumber b) {
        // If either of number is zero then result will be zero
        if (a.toString().length() == 0 || b.toString().length() == 0)
            return new BigNumber((long) 0);
        // If either of number is single digit then we find
        // the product of x,y by adding x to itself y times
        if (a.magnitude.size() == 1 || (b.magnitude.size() == 1)) {
            BigNumber result = new BigNumber((long) 0);
            if (a.magnitude.size() == 1) {
                for (Long i = a.magnitude.get(0); i > 0; i--)
                    result = add(result, b);
            } else {
                for (Long i = b.magnitude.get(0); i > 0; i--)
                    result = add(result, a);
            }
            return result;
        }
        // Dividing both the numbers into two parts each
        BigNumber a1 = new BigNumber();
        BigNumber a2 = new BigNumber();
        BigNumber b1 = new BigNumber();
        BigNumber b2 = new BigNumber();
        int divisionLimit;
        // Half the Length of smaller number is taken as division limit
        if (a.magnitude.size() <= b.magnitude.size())
            divisionLimit = a.magnitude.size() / 2;
        else
            divisionLimit = b.magnitude.size() / 2;
        a1.magnitude = a.magnitude.subList(divisionLimit, a.magnitude.size());
        a2.magnitude = a.magnitude.subList(0, divisionLimit);
        b1.magnitude = b.magnitude.subList(divisionLimit, b.magnitude.size());
        b2.magnitude = b.magnitude.subList(0, divisionLimit);
        BigNumber term1 = product(a1, b1);
        BigNumber term2 = product(a2, b2);
        BigNumber term3 = product(add(a1, a2), add(b1, b2));
        BigNumber middleTerm = subtract(subtract(term3, term1), term2);
        for (int i = 0; i < 2 * divisionLimit; i++)
            term1.magnitude.add(0, (long) 0);
        for (int i = 0; i < divisionLimit; i++)
            middleTerm.magnitude.add(0, (long) 0);
        BigNumber product = add(add(term2, middleTerm), term1);
        // If the numbers have different signs then the product will result in negative number
        if (a.negative != b.negative)
            product.negative = true;
        return product;
    }

    /**
     * Function to find the value of a Big number raised to the power of a long digit
     *
     * @param a Big number
     * @param n long number
     * @return Big number raised to the power of long number
     * (BigNumber ^ n)
     */
    public static BigNumber power(BigNumber a, long n) {
        if (n == 0)                 // Any number raised to the power zero will result in one
            return new BigNumber((long) 1);
        else if (n == 1)            // Any number raised to the power one will not change
            return a;
        BigNumber power = power(product(a, a), n / 2);
        if (n % 2 == 0) {
            power.negative = false;
            return power;
        } else {
            power.negative = true;
            return product(power, a);
        }
    }

    /**
     * Function to find the value of Big number raised to the power of another big number
     *
     * @param a First Big number
     * @param b Second Big number
     * @return First Big number raised to the power of Second
     * (FirstBigNumber ^ SecondBigNumber)
     */
    public static BigNumber power(BigNumber a, BigNumber b) {
        // Any number raised to the power zero will result in one
        if (b.toString().length() == 0)
            return new BigNumber((long) 1);
            // Any number raised to the power one will not change
        else if (b.toString().length() == 1 && b.magnitude.get(0) == 1)
            return a;
        BigNumber power = power(product(a, a), divide(b, new BigNumber((long) 2)));
        if (b.magnitude.get(0) % 2 == 0) {
            power.negative = false;
            return power;
        } else {
            // A negative number raised to any odd power will result in negative
            if (a.negative)
                power.negative = true;
            return product(power, a);
        }
    }

    /**
     * Function to find the sign bit for the result of division operation
     *
     * @param a First Big number
     * @param b Second Big number
     * @return Quotient when first Big number is divided by the second Big number
     * along with the sign bit truncated to decimal part
     * (FirstBigNumber / SecondBigNumber)
     */
    public static BigNumber divide(BigNumber a, BigNumber b) {
        BigNumber division;
        if (a.negative != b.negative) {
            if (a.negative) {             // If a is negative and b is positive
                a.toggle();
                division = divideWithoutSign(a, b);
                a.toggle();
            } else {                       // If b is negative and a is positive
                b.toggle();
                division = divideWithoutSign(a, b);
                b.toggle();
            }
            // If the sign of both the numbers do not match then the result will be negative
            division.negative = true;
        }
        // If both the numbers have same sign then the result will always be positive
        else
            division = divideWithoutSign(a, b);
        return division;
    }

    /**
     * Function to find the Quotient when First Big number is divided by the second Big number
     *
     * @param a First Big number
     * @param b Second Big number
     * @return Quotient when first Big number is divided by the second Big number
     * truncated to decimal part
     * (FirstBigNumber / SecondBigNumber)
     */
    private static BigNumber divideWithoutSign(BigNumber a, BigNumber b) {
        int comparison = a.compareTo(b);
        if (comparison < 0)
            return new BigNumber((long) 0);
        else if (comparison == 0)
            return new BigNumber((long) 1);
        BigNumber QuotientMin = new BigNumber((long) 1);
        BigNumber QuotientMax = a;
        while (QuotientMin.compareTo(QuotientMax) < 0) {
            BigNumber QuotientMid = findMid(QuotientMin, QuotientMax);
            if (QuotientMin.compareTo(QuotientMid) == 0)
                break;
            BigNumber guessResult = product(QuotientMid, b);
            if (guessResult.compareTo(a) < 0)
                QuotientMin = QuotientMid;
            else if (guessResult.compareTo(a) > 0)
                QuotientMax = QuotientMid;
            else
                return QuotientMid;
        }
        return QuotientMin;
    }

    /**
     * Function to find the Remainder when First Big number is divided by the second Big number
     *
     * @param a First Big number
     * @param b Second Big number
     * @return Remainder when first Big number is divided by the second Big number
     * (FirstBigNumber % SecondBigNumber)
     */
    public static BigNumber mod(BigNumber a, BigNumber b) {
        BigNumber Remainder;
        if (a.negative != b.negative) {
            if (a.negative) {         // If a is negative and b is positive
                a.toggle();
                Remainder = subtract(b, subtract(a, product(b, divide(a, b))));
                a.toggle();
            } else {                   // If b is negative and a is positive
                b.toggle();
                Remainder = subtract(b, subtract(a, product(b, divide(a, b))));
                b.toggle();
                Remainder.negative = true;
            }
        } else {
            if (a.negative) {         // If both a and b are negative
                a.toggle();
                b.toggle();
                Remainder = divide(a, b);
                a.toggle();
                b.toggle();
            } else                    // If both a and b are positive
                Remainder = divide(a, b);
            Remainder = subtract(a, product(b, Remainder));
        }
        return Remainder;
    }

    /**
     * Function to find the square root of Big number
     *
     * @param a Big number
     * @return square root of Big number truncated to decimal part
     * (BigNumber)^(1/2)
     */
    public static BigNumber squareRoot(BigNumber a) {
        BigNumber min = new BigNumber((long) 0);
        BigNumber max = findMid(min, a);
        while (min.compareTo(max) < 0) {
            BigNumber mid = findMid(min, max);
            if (min.compareTo(mid) == 0)
                break;
            BigNumber square = product(mid, mid);
            int comparison = square.compareTo(a);
            if (comparison < 0)
                min = mid;
            else if (comparison > 0)
                max = mid;
            else
                return mid;
        }
        return min;
    }

    /**
     * Function to find middle number of two big numbers
     *
     * @param a First Big number
     * @param b Second Big number
     * @return average of two Big numbers
     * (FirstBigNumber+SecondBigNumber)/2
     */
    public static BigNumber findMid(BigNumber a, BigNumber b) {
        BigNumber sum = add(a, b);
        BigNumber QuotientMid = new BigNumber();
        ArrayList<Long> mid = new ArrayList<>();
        long carry = 0;
        for (int i = sum.magnitude.size() - 1; i >= 0; i--) {
            if (sum.magnitude.get(i) / 2 == 0 && i == sum.magnitude.size() - 1) {
                carry = 1;
                continue;
            }
            long digit = carry * base + sum.magnitude.get(i);
            mid.add(digit / 2);
            carry = digit % 2;
        }
        for (int i = mid.size() - 1; i >= 0; i--)
            QuotientMid.magnitude.add(mid.get(i));
        return QuotientMid;
    }

    /**
     * Function to find factorial of a big number
     *
     * @param num Big number
     * @return factorial of a big number
     * (BigNumber!)
     */
    public static BigNumber factorial(BigNumber num) {
        String number = num.toString();
        if (number.charAt(0) == '1' && number.length() == 1) return new BigNumber((long) 1);
        return product(num, factorial(subtract(num, new BigNumber("1"))));
    }

}