/*
Implement a recursive algorithm without recursion, by using a stack to simulate recursion on Binary Tree Traversals

 */
import java.util.Stack;

class BTNode { //Node of a Binary Tree
    BTNode left, right;
    int data;

    public BTNode() {
        left = null;
        right = null;
        data = 0;
    }

    public BTNode(int n) {
        left = null;
        right = null;
        data = n;
    }
}

class BT {  //Binary Tree Class
    private BTNode root;

    public BT() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void insert(int data) {
        root = insert(root, data);
    }

    private BTNode insert(BTNode node, int data) {
        if (node == null)
            node = new BTNode(data);
        else {
            if (node.right == null)
                node.right = insert(node.right, data);
            else
                node.left = insert(node.left, data);
        }
        return node;
    }

    public void inorder() {
        inorder(root);
    }

    private void inorder(BTNode node) { //have a stack holding the left child values.
        if (node == null)
            return;
        Stack<BTNode> st = new Stack<>();
        while (node != null) {
            st.push(node);
            node = node.left;
        }
        while (!st.empty()) { //pop from the stack and print that element
            node = st.pop();
            System.out.print(node.data + " ");
            node = node.right;
            while (node != null) {
                st.push(node); //push right child into stack and then keep pushing all left child's of that.
                node = node.left;
            }
        }
    }

    public void preorder() {
        preorder(root);
    }

    private void preorder(BTNode r) { //keep printing the left most elements until we get null and pushing the right child into the stack
        Stack<BTNode> st = new Stack<>();//if right child has a left child then do the same for that
        st.push(null);
        while (r != null) {
            System.out.print(r.data + " ");
            if (r.right != null)
                st.push(r.right);
            if (r.left != null)
                r = r.left;
            else
                r = st.pop();
        }
    }

    public void postorder() {
        postorder(root);
    }

    private void postorder(BTNode r) { //we have two stacks for post order, one for each child
        if(r==null) return;
        Stack<BTNode> st1 = new Stack<>();
        Stack<BTNode> st2 = new Stack<>();
        st1.push(r);
        while (!st1.empty()) {
            BTNode temp = st1.pop();
            if (temp.left != null)
                st1.push(temp.left);
            if (temp.right != null)
                st1.push(temp.right);
            st2.push(temp);
        }
        while (!st2.empty())
            System.out.print(st2.pop().data + " ");
    }
}

public class Program4 {
    public static void main(String[] args) {
        BT bt = new BT();
        for (int i = 1; i < 11; i++)
            bt.insert(new Integer(i));
        System.out.println("Traversal without using recursion of a Binary Tree");
        System.out.print("Post order : ");
        bt.postorder();
        System.out.print("\nPre order : ");
        bt.preorder();
        System.out.print("\nIn order : ");
        bt.inorder();
    }
}