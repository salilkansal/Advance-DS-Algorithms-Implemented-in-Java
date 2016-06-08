/*
Merge sort algorithm that works on linked lists
 */
class SortableList<T extends Comparable<? super T>> {
    Entry<T> header, tail;
    int size;

    SortableList() {
        header = new Entry<>(null, null);
        tail = null;
        size = 0;
    }

    public void add(T x) {
        if (tail == null) {
            header.next = new Entry<>(x, header.next);
            tail = header.next;
        } else {
            tail.next = new Entry<>(x, null);
            tail = tail.next;
        }
        size++;
    }

    public void printList() {
        Entry<T> x = header.next;
        while (x != null) {
            System.out.print(x.element + " ");
            x = x.next;
        }
        System.out.println();
    }

    private Entry<T> merge(Entry<T> list1, Entry<T> list2, int num) {
        int pos1 = 0;
        int pos2 = 0;
        int leftBound = num / 2;
        int rightBound = num - (num / 2);
        Entry<T> start;
        if (list1.element.compareTo(list2.element) <= 0) {
            start = list1;
            pos1++;
            list1 = list1.next;
        } else {
            start = list2;
            pos2++;
            list2 = list2.next;
        }
        Entry<T> temp = start;
        while (pos1 < leftBound && pos2 < rightBound) {
            if (list1.element.compareTo(list2.element) <= 0) { //add smaller element from two list every time
                temp.next = list1;
                pos1++;
                temp = temp.next;
                list1 = list1.next;
            } else {
                temp.next = list2;
                pos2++;
                temp = temp.next;
                list2 = list2.next;
            }
        }
        while (pos1 < leftBound) { //check if any more elements are left, then add them
            temp.next = list1;
            pos1++;
            temp = temp.next;
            list1 = list1.next;
        }
        while (pos2 < rightBound) {
            temp.next = list2;
            pos2++;
            temp = temp.next;
            list2 = list2.next;
        }
        temp.next = null;
        return start;
    }

    private Entry<T> mergeSort(Entry<T> start, int num) {
        if (num < 2) //return directly if only 1 element present
            return start;
        Entry<T> ptrMiddle = start; //finding middle element by 2 pointer technique
        int index = 0;
        while (index < num / 2) {
            ptrMiddle = ptrMiddle.next;
            index++;
        }
        start = mergeSort(start, num / 2); //apply mergeSort recursively on left half and right half of list
        ptrMiddle = mergeSort(ptrMiddle, num - (num / 2));
        return merge(start, ptrMiddle, num);//merge the sorted lists into one sorted list
    }

    public void mergeSort() { //helper function for mergeSorting
        header.next = mergeSort(header.next, size);
    }

    private class Entry<T> {
        T element;
        Entry<T> next;

        Entry(T x, Entry<T> nxt) {
            element = x;
            next = nxt;
        }
    }
}

public class Program3 {
    public static void main(String[] args) {
        SortableList<Integer> lst = new SortableList<>();
        for (int i = 10; i > 0; i--) {
            lst.add(i);
        }
        System.out.print("Original list : ");
        lst.printList();
        lst.mergeSort();
        System.out.print("Sorted list : ");
        lst.printList();
    }
}