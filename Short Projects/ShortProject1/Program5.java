/*
Extend the "unzip" algorithm to "multiUnzip" on the SinglyLinkedList class

 */
import java.util.Scanner;

class Entry<T> {
    T element;
    Entry<T> next;
    Entry(T x, Entry<T> nxt) {
        element = x;
        next = nxt;
    }
}

class SinglyLinkedList<T> {
    Entry<T> header, tail;
    int size;
    SinglyLinkedList() {
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
}

class State<E> { //state class for the finite state machine
    SinglyLinkedList<E> list;
    public State() {
        list = new SinglyLinkedList<>();
    }
}

class Unzipper<E> {
    State<E>[] states;
    Unzipper(int k) {
        states = new State[k]; // creates k finite state machines
        for (int i = 0; i < states.length; i++)
            states[i] = new State<>();
    }
    void multiUnzip(SinglyLinkedList<E> originalList) {
        int currState = 0; //let the starting state be state 0
        Entry<E> ptr = originalList.header.next;
        while (ptr != null) {
            states[currState].list.add(ptr.element); //keep adding the element to the current state list
            currState = (currState+1)%states.length; //change state to the next state
            ptr = ptr.next; //increment the pointer of input list
        }
        for (int i = 1; i < states.length; i++) //finally merge the lists of each state
            states[i-1].list.tail.next = states[i].list.header.next;
        originalList.header = states[0].list.header;
    }
}
public class Program5 {
    public static void main(String[] args) {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        System.out.print("Enter the K value for unzip : ");
        Scanner in = new Scanner(System.in);
        int k = in.nextInt();
        for (int i = 0; i < 12; i++)
            list.add(i);
        Unzipper<Integer> unzip = new Unzipper<>(k);
        System.out.print("List before unzip : ");
        list.printList();
        unzip.multiUnzip(list);
        System.out.print("List after unzip : ");
        list.printList();
    }
}