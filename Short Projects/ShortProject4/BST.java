import java.util.ArrayDeque;
import java.util.List;
import java.util.Scanner;

/**
 * Class that represents BST and its functions
 *
 * @param <T> Generic type
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * @version 1.0
 * @since 2016-03-09
 */

public class BST<T extends Comparable<? super T>> {
    class Entry<T> {
        T element;
        Entry<T> left, right, parent;

        Entry(T x, Entry<T> l, Entry<T> r, Entry<T> p) {
            element = x;
            left = l;
            right = r;
            parent = p;
        }
    }

    Entry<T> root;
    int size;
    boolean removeRightChild = true;

    BST() {
        root = null;
        size = 0;
    }

    BST(T[] arr) {
        size = arr.length;
        root = buildFromArray(arr, 0, size - 1, null);
    }

    /**
     * Function to build BST given a sorted array
     *
     * @param arr    : Sorted array
     * @param low    : Start index
     * @param high   : End index
     * @param parent : Parent pointer
     * @return : Child pointer
     */
    public Entry<T> buildFromArray(T[] arr, int low, int high, Entry<T> parent) {
        if (high < low)
            return null;
        int middle = (low + high) / 2;
        Entry<T> node = new Entry<>(arr[middle], null, null, parent);
        node.left = buildFromArray(arr, low, middle - 1, node);
        node.right = buildFromArray(arr, middle + 1, high, node);
        return node;
    }

    BST(List<T> lst) {
        size = lst.size();
        root = buildFromList(lst, null);
    }

    /**
     * @param lst    The input list
     * @param parent A parent pointer for list
     * @return The new node made from middle element.
     */
    public Entry<T> buildFromList(List<T> lst, Entry<T> parent) {
        if (lst.isEmpty())
            return null;
        int bound = lst.size() / 2;
        Entry<T> node = new Entry<>(lst.get(bound), null, null, parent);
        node.left = buildFromList(lst.subList(0, bound), node);
        node.right = buildFromList(lst.subList(bound + 1, lst.size()), node);
        return node;
    }

    // Find x in subtree rooted at node t. Returns node where search ends.
    Entry<T> find(Entry<T> t, T x) {
        Entry<T> pre = t;
        while (t != null) {
            pre = t;
            int cmp = x.compareTo(t.element);
            if (cmp == 0)
                return t;
            else if (cmp < 0)
                t = t.left;
            else
                t = t.right;
        }
        return pre;
    }

    // Is x contained in tree?
    public boolean contains(T x) {
        Entry<T> node = find(root, x);
        return node == null ? false : x.equals(node.element);
    }

    // Add x to tree. If tree contains a node with same key, replace element by x.
    // Returns true if x is a new element added to tree.
    public boolean add(T x) {
        if (size == 0)
            root = new Entry<>(x, null, null, null);
        else {
            Entry<T> node = find(root, x);
            int cmp = x.compareTo(node.element);
            if (cmp == 0) {
                node.element = x;
                return false;
            }
            Entry<T> newNode = new Entry<>(x, null, null, node);
            if (cmp < 0)
                node.left = newNode;
            else
                node.right = newNode;
        }
        size++;
        return true;
    }

    // Remove x from tree. Return x if found, otherwise return null
    public T remove(T x) {
        T rv = null;
        if (size > 0) {
            Entry<T> node = find(root, x);
            if (x.equals(node.element)) {
                rv = node.element;
                remove(node);
                size--;
            }
        }
        return rv;
    }

    // Called when node has at most one child. Returns that child.
    Entry<T> oneChild(Entry<T> node) {
        return node.left == null ? node.right : node.left;
    }

    // Remove a node from tree
    void remove(Entry<T> node) {
        if (node.left != null && node.right != null)
            removeTwo(node);
        else
            removeOne(node);
    }

    // remove node that has at most one child
    void removeOne(Entry<T> node) {
        if (node == root)
            root = oneChild(root);
        else {
            Entry<T> p = node.parent;
            if (p.left == node)
                p.left = oneChild(node);
            else
                p.right = oneChild(node);
        }
    }

    // remove node that has two children
    void removeTwo(Entry<T> node) {
        if (removeRightChild) {
            Entry<T> minRight = node.right;
            while (minRight.left != null)
                minRight = minRight.left;
            node.element = minRight.element;
            removeOne(minRight);
        } else {
            Entry<T> minLeft = node.left;
            while (minLeft.right != null)
                minLeft = minLeft.right;
            node.element = minLeft.element;
            removeOne(minLeft);
        }
        removeRightChild = !removeRightChild;
    }

    public static void main(String[] args) {
        BST<Integer> t = new BST<>();
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int x = in.nextInt();
            if (x > 0) {
                System.out.print("Add " + x + " : ");
                t.add(x);
                t.printTree();
            } else if (x < 0) {
                System.out.print("Remove " + x + " : ");
                t.remove(-x);
                t.printTree();
            } else {
                Comparable[] arr = t.toArray();
                System.out.print("Final: ");
                for (int i = 0; i < t.size; i++)
                    System.out.print(arr[i] + " ");
                System.out.println();
                Comparable[] levelarr = t.levelOrderArray();
                System.out.print("Level Order Traversal: ");
                for (int i = 0; i < t.size; i++)
                    System.out.print(levelarr[i] + " ");
                System.out.println();
                return;
            }
        }
    }

    // Create an array with the elements using in-order traversal of tree
    public Comparable[] toArray() {
        Comparable[] arr = new Comparable[size];
        inOrder(root, arr, 0);
        return arr;
    }

    // Recursive in-order traversal of tree rooted at "node".
    // "index" is next element of array to be written.
    // Returns index of next entry of arr to be written.
    int inOrder(Entry<T> node, Comparable[] arr, int index) {
        if (node != null) {
            index = inOrder(node.left, arr, index);
            arr[index++] = node.element;
            index = inOrder(node.right, arr, index);
        }
        return index;
    }

    /**
     * Function to find elements using level order traversal of BST
     *
     * @return Level order elements in an array
     */
    private Comparable[] levelOrderArray() {
        Comparable[] arr = new Comparable[size];
        ArrayDeque<Entry> queue = new ArrayDeque<>();
        queue.add(root);
        int index = 0;
        while (!queue.isEmpty()) {
            Entry<T> node = queue.poll();
            arr[index++] = node.element;
            if (node.left != null)
                queue.add(node.left);
            if (node.right != null)
                queue.add(node.right);
        }
        return arr;
    }

    public void printTree() {
        System.out.print("[" + size + "]");
        printTree(root);
        System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry<T> node) {
        if (node != null) {
            printTree(node.left);
            System.out.print(" " + node.element);
            printTree(node.right);
        }
    }
}

/*
Sample input:
1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0


*/