/*
functions for adding and subtracting large numbers where each digit is stored as a separate node of the list
 */

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Program2 {
    public static void main(String[] args) {
        LinkedList<Integer> ls1 = new LinkedList<>();
        LinkedList<Integer> ls2 = new LinkedList<>();
        String num1 = "9999";//sample inputs taken in String form
        String num2 = "8888";

        for (int i = num1.length() - 1; i >= 0; i--) {
            ls1.add(Integer.parseInt(String.valueOf(num1.charAt(i)))); //converting the string to List. LSD is in the head
        }

        for (int i = num2.length() - 1; i >= 0; i--) {
            ls2.add(Integer.parseInt(String.valueOf(num2.charAt(i))));
        }

        System.out.println("Num 1: "+ ls1);
        System.out.println("Num 2: "+ ls2);
        int base = 10;

        LinkedList<Integer> addition = new LinkedList<>();
        LinkedList<Integer> subtraction = new LinkedList<>();

        add(ls1, ls2, addition, base);
        subtract(ls1, ls2, subtraction, base);
        System.out.println("Addition : " + addition);
        System.out.println("Subtraction : " + subtraction);
    }

    public static void add(List<Integer> ls1, List<Integer> ls2, List<Integer> addition, int base) {
        int carry = 0; //default carry value is 0
        ListIterator<Integer> l1 = ls1.listIterator();
        ListIterator<Integer> l2 = ls2.listIterator();
        while (l1.hasNext() && l2.hasNext()) {
            int x = l1.next();
            int y = l2.next();
            addition.add((carry + x + y) % base); //the remainder goes as sum
            carry = (carry + x + y) / base; // quotient becomes the new carry
        }
        while (l1.hasNext()) {
            int x = l1.next();
            addition.add((carry + x) % base);
            carry = (carry + x) / base; //if list2 ends then just add carry to list1 element
        }
        while (l2.hasNext()) {
            int y = l2.next();
            addition.add((carry + y) % base);
            carry = (carry + y) / base; //if list1 ends then just add carry to list2 element
        }
        if (carry != 0)
            addition.add(carry); //if new list has more number of digits then just add carry
    }

    public static void subtract(List<Integer> ls1, List<Integer> ls2, List<Integer> subtraction, int base) {
        int borrow = 0; //default borrow value is 0
        ListIterator<Integer> l1 = ls1.listIterator();
        ListIterator<Integer> l2 = ls2.listIterator();
        while (l2.hasNext()) {
            int x = l1.next();
            int y = l2.next();
            if (x != 0) {
                x = x - borrow; //decrement the borrow value
                if (x < y)
                    borrow = 1; //take borrow if upper number is smaller
                else
                    borrow = 0;
                x = borrow * base + x; //add the borrow if needed from higher significant digit
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
            subtraction.add(x - y);
        }
        while (l1.hasNext()) { //if list1 has elements left then keep adding them after considering the borrow value
            int x = l1.next();
            if (x != 0) {
                x = x - borrow;
                borrow = 0;
            } else {
                if (borrow == 1)
                    x = base - 1;
            }
            if (!l1.hasNext() && x == 0)
                break;
            subtraction.add(x);
        }
    }
}