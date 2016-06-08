import java.util.*;

//Only those objects can be passed which can be compared with themselves
class MergeSort<T extends Comparable<? super T>> {

    private T array[];

    public MergeSort(T[] array) {
        this.array = array;
    }

    public T[] getArr() {
        return array;
    }


    public void mergeSort(int low, int high) {
        if (low >= high) return; //return without doing anything if only one element
        int mid = (low + high) / 2; //split array into two halves
        mergeSort(low, mid);
        mergeSort(mid + 1, high);
        merge(low, mid, high); //calling the merge method

    }

    private void merge(int low, int mid, int high) {
        ArrayDeque<T> auxList = new ArrayDeque<>(); //using ArrayDeque as auxiliary storage of elements
        int arr1ptr, arr1end, arr2ptr, arr2end;
        arr1ptr = low;
        arr1end = mid;
        arr2ptr = mid + 1;
        arr2end = high;
        while ((arr1ptr <= arr1end) && (arr2ptr <= arr2end)) { //loop till anyone of the arrays become empty.
            if (array[arr1ptr].compareTo(array[arr2ptr]) < 0) { //adding the smaller element to the temp list
                auxList.add(array[arr1ptr++]);
            } else {
                auxList.add(array[arr2ptr++]);
            }
        }
        //copying rest of the elements into temp list
        if (arr1ptr > arr1end) {
            for (int k = arr2ptr; k <= arr2end; k++) {
                auxList.add(array[k]);
            }
        } else {
            for (int k = arr1ptr; k <= arr1end; k++) {
                auxList.add(array[k]);
            }
        }
        //copy temp list back to main array
        for (int k = low; k <= high; k++) {
            array[k] = auxList.removeFirst();

            /** using arrayList will be expensive here as while
             * removing the first element, rest of the elements
             * will have to be shifted by one step.
             */
        }


    }

}

class HeapSort<T extends Comparable<? super T>> {
    //using a priority queue to implement the Heapsort algorithms
    private PriorityQueue<T> priorityQueue = new PriorityQueue<>();

    //inputs a collection and adds all the elements to a priority queue
    public HeapSort(Collection<? extends T> collection) {
        priorityQueue.addAll(collection);
    }

    //removes the root of the heap and adds it to the arrayList
    public ArrayList<T> getSortedList() {
        ArrayList<T> array = new ArrayList<>(priorityQueue.size());
        int s = priorityQueue.size();
        for (int i = 0; i < s; i++) {
            array.add(i, priorityQueue.poll());
        }
        return array;
    }


}

public class Sort {

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Program to compare Merge Sort and Heap Sort over >1 million numbers");
        System.out.println("Which sorting algorithm you would like to test?");
        System.out.println("1) Merge Sort");
        System.out.println("2) Heap Sort");
        int choice = scanner.nextInt();

        switch (choice) {

            case 1:
                System.out.println("Merge Sort selected");
                System.out.println("Generating array of 1 million numbers");
                Integer[] array = new Integer[1000000];

                for (int i = 0; i < array.length; i++) {
                    array[i] = -i;
                }

                System.out.println("Array Generated");
                System.out.println("Running Heap Sort");
                MergeSort<Integer> m1 = new MergeSort<>(array);
                Long start1 = System.currentTimeMillis();
                m1.mergeSort(0, m1.getArr().length - 1);
                Long end1 = System.currentTimeMillis();
                System.out.println("Finished Successfully");
                System.out.println("Took " + (end1 - start1) + " ms");
                break;
            case 2:
                System.out.println("Heap Sort selected");
                System.out.println("Generating array of 1 million numbers");
                ArrayList<Integer> array1 = new ArrayList<>();

                for (int i = 0; i < 1000000; i++) {
                    array1.add(-i);
                }

                System.out.println("Array Generated");
                System.out.println("Running Heap Sort");
                HeapSort<Integer> heapSort = new HeapSort<>(array1);
                Long start = System.currentTimeMillis();
                heapSort.getSortedList();
                Long end = System.currentTimeMillis();
                System.out.println("Finished Successfully");
                System.out.println("Took " + (end - start) + " ms");
                break;
            default:
                break;


        }


    }
}

