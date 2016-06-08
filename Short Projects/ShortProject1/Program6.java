/*
Write recursive and nonrecursive functions for the following tasks:
   (i) reverse the order of elements of the SinglyLinkedList class
   (ii) print the elements of the SinglyLinkedList class, in reverse order.
   Write the code and annotate it with  proper loop invariants.
 */
import java.util.Stack;

public class Program6<T> {
    public class Entry<T> {
        T element;
        Entry<T> next;

        Entry(T x, Entry<T> nxt) {
            element = x;
            next = nxt;
        }
    }


    Entry<T> header, tail;
    int size;

    Program6() {
        header = new Entry<>(null, null);
        tail = null;
        size = 0;
    }

    void add(T x) {
        if (tail == null) {
            header.next = new Entry<>(x, header.next);
            tail = header.next;
        } else {
            tail.next = new Entry<>(x, null);
            tail = tail.next;
        }
        size++;
    }

    void printList() {
        Entry<T> x = header.next;
        while (x != null) {
            System.out.print(x.element + " ");
            x = x.next;
        }
        System.out.println();
    }

    void reverseList() { //reversing non-recursively
        Entry<T> curr = header.next;
        if (curr != null) { //loop till end is reached
            Entry<T> prev = curr, next = curr.next;
            curr.next = null;
            while (next != null) {
                curr = next;
                next = curr.next;
                curr.next = prev; //reversing the pointer
                prev = curr; //incrementing the pointer
                header.next = curr;
            }
        }
    }

    void printReverse() {
        //add elements to stack and then pop till stack is empty and print the popped element
        Entry<T> x = header.next;
        if (x != null) {
            Stack<T> stack = new Stack<>();
            while (x != null) {
                stack.add(x.element);
                x = x.next;
            }
            while (!stack.isEmpty())
                System.out.print(stack.pop() + " ");
        }
    }

    void reverse() { //helper function for reversing recursively
        Entry<T> x = header.next;
        if (x != null) {
            Entry<T> y = x.next;
            reverseRecursive(x, y);
        }
    }

    private void reverseRecursive(Entry<T> prev, Entry<T> curr) {
        if (curr == null) {
            header.next = prev;
            return;
        }
        reverseRecursive(curr, curr.next); //call for next set of pointers
        curr.next = prev; //reverse the pointer
        prev.next = null;
    }

    public void printListReverse() { //helper function for printing list recursively
        Entry<T> x = header.next;
        printReverseRecursive(x);
    }

    private void printReverseRecursive(Entry<T> x) {
        if (x == null)
            return;
        printReverseRecursive(x.next); //first call for next element then print current element
        System.out.print(x.element + " ");
    }

    public static void main(String[] args) {
        int n = 10;
        if (args.length > 0) {
            n = Integer.parseInt(args[0]);
        }
        Program6<Integer> lst = new Program6<>();
        for (int i = 1; i <= n; i++) {
            lst.add(i);
        }
        System.out.print("Original List : ");
        lst.printList();
        lst.reverseList();
        System.out.print("Reverse List without recursion : ");
        lst.printList();
        System.out.println();
        lst.reverseList();
        lst.reverse();
        System.out.print("Reverse List using recursion : ");
        lst.printList();
        System.out.println();
        lst.reverse();
        System.out.print("Print the list in reverse order using recursion : ");
        lst.printListReverse();
        System.out.println();
        System.out.println();

        System.out.print("Print the list in reverse order without recursion : ");
        lst.printReverse();
        System.out.println();
    }
}