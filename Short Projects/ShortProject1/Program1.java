/*Implementing union, intersection, and set difference functions of the sets for two sorted linked lists
*/

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Program1 {
    public static void main(String[] args) {
        LinkedList<Integer> ls1 = new LinkedList<>();
        LinkedList<Integer> ls2 = new LinkedList<>();
        for (int i = 0; i < 25; i++)  //list of numbers from 0 to 24
            ls1.add(i);
        for (int i = 1; i < 25; i += 2) //list of odd numbers from 0 to 24
            ls2.add(i);
        LinkedList<Integer> intersection = new LinkedList<>();
        LinkedList<Integer> union = new LinkedList<>();
        LinkedList<Integer> setDifference = new LinkedList<>();
        findIntersection(ls1, ls2, intersection);
        findUnion(ls1, ls2, union);
        findSetDifference(ls1, ls2, setDifference);
        System.out.println("Set 1 : " + ls1);
        System.out.println("Set 2 : " + ls2);
        System.out.println("Intersection : " + intersection);
        System.out.println("Union : " + union);
        System.out.println("Set Difference : " + setDifference);
    }

    /*
        element1-contains current value of list1
        element2-contains current value of list2
        advance1-specify whether list1 pointer should be incremented
        advance2-specify whether list2 pointer should be incremented

     */
    public static <T extends Comparable<? super T>> void findIntersection(List<T> ls1, List<T> ls2, List<T> intersection) {
        ListIterator<T> lstIter1 = ls1.listIterator();
        ListIterator<T> lstIter2 = ls2.listIterator();
        T element1 = null;
        T element2 = null;
        Boolean advance1 = true, advance2 = true;

        while (lstIter1.hasNext() && lstIter2.hasNext()) {//iterate both lists till either of them ends
            if (advance1)
                element1 = lstIter1.next();
            if (advance2)
                element2 = lstIter2.next();
            if (element1.compareTo(element2) == 0) {//if element is common in both the lists, add it to the intersection list
                intersection.add(element1);
                advance1 = true;
                advance2 = true;
            } else if (element1.compareTo(element2) < 0) {
                advance2 = false;
                advance1 = true;
            } else {
                advance1 = false;
                advance2 = true;
            }
        }
        if (lstIter1.hasNext() && advance1) {//comparing rest of the elements in the list
            element1 = lstIter1.next();
            if (element1.compareTo(element2) == 0)
                intersection.add(element1);
        } else if (lstIter2.hasNext() && advance2) {
            element2 = lstIter2.next();
            if (element1.compareTo(element2) == 0)
                intersection.add(element1);
        }
    }


    public static <T extends Comparable<? super T>> void findUnion(List<T> ls1, List<T> ls2, List<T> union) {
        Iterator<T> lstIter1 = ls1.iterator();
        Iterator<T> lstIter2 = ls2.iterator();
        T element1 = null, element2 = null;
        Boolean advance1 = true, advance2 = true;
        while (lstIter1.hasNext() && lstIter2.hasNext()) {
            if (advance1)
                element1 = lstIter1.next();
            if (advance2)
                element2 = lstIter2.next();
            if (element1.compareTo(element2) == 0) {//if both lists have the same element add any one to the union list
                union.add(element1);
                advance1 = true;
                advance2 = true;
            } else if (element1.compareTo(element2) < 0) {
                union.add(element1);
                advance1 = true;
                advance2 = false;
            } else {
                union.add(element2);
                advance2 = true;
                advance1 = false;
            }
        }
        while (lstIter1.hasNext())
            union.add(lstIter1.next());
        while (lstIter2.hasNext())
            union.add(lstIter2.next());
    }

    public static <T extends Comparable<? super T>> void findSetDifference(List<T> ls1, List<T> ls2, List<T> setDifference) {
        ListIterator<T> lstIter1 = ls1.listIterator();
        ListIterator<T> lstIter2 = ls2.listIterator();
        T element1 = null, element2 = null;
        Boolean advance1 = true, advance2 = true;
        while (lstIter1.hasNext() && lstIter2.hasNext()) {
            if (advance1)
                element1 = lstIter1.next();
            if (advance2)
                element2 = lstIter2.next();
            if (element1.compareTo(element2) < 0) {//add only those elements to the list that are in list1 and not in list2
                setDifference.add(element1);
                advance1 = true;
                advance2 = false;
            } else if (element1.compareTo(element2) > 0) {
                advance1 = false;
                advance2 = true;
            } else {//when an element is common to both the lists then leave that element
                advance1 = true;
                advance2 = true;
            }
        }
        while (lstIter1.hasNext()) {
            element1 = lstIter1.next();
            if (element1.compareTo(element2) != 0)
                setDifference.add(element1);
        }
    }
}