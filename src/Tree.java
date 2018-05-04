//CSE 205: MW 4:30
//Assignment: 6
//Author: Trevor Angle, 1213009731
//Description: Implementation of a binary tree from another assignment. The major changes are making the tree Iterable
//              and Serializable

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Tree<T extends Comparable<T>> implements Serializable, Iterable<T> {
    private Node root;
    private int size;
    private static final long serialVersionUID = 2L;

    public void insert(T item) {
        /*Attempts to insert item into tree*/
        if (size != 0) {
            root.insert(item);
        } else {
            root = new Node(item);
            size = 1;
        }
    }

    public boolean contains(T item) {
        /*Returns whether the tree contains "item"*/
        return size != 0 && root.contains(item);
    }

    public void printInOrder() {
        /*Prints with the toString() method*/
        System.out.println(this);
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        /*Returns inorder string if the tree has nodes*/
        if (size != 0) {
            return root.toString();
        } else {
            return "";
        }
    }

    @Override
    public Iterator<T> iterator() {
        /*Returns the Nodes' iterator*/
        if (size > 0) {
            return root.iterator();
        }
        return Collections.emptyIterator();
    }

    /*The inner Node class has also been made Iterable and Serializable (the members of anything you want to serialize
    * have to either be serializable or transient)*/
    private class Node implements Iterable<T>, Serializable {
        private T data;
        private Node left;
        private Node right;
        private static final long serialVersionUID = 4L;

        Node() {
            /*Recreation of default constructor, with values initialized to null for funsies*/
            data = null;
            left = null;
            right = null;
        }

        Node(T data) {
            /*Constructor to set data right away*/
            this.data = data;
            left = null;
            right = null;
        }

        void insert(T item) {
            /*Recursively finds the spot (if any) where "item" should be inserted*/
            if (data == null) {
                data = item;
                //size is only incremented once we have placed data in a previously null node
                size++;
            }
            else if (item.compareTo(data) < 0) {
                if (left == null) {
                    left = new Node();
                }
                left.insert(item);
            }
            else if (item.compareTo(data) > 0) {
                if (right == null) {
                    right = new Node();
                }
                right.insert(item);
            }
            //else if the items are equal, we don't want to insert the new one at all
        }

        boolean contains(T item) {
            /*Recursively checks whether the tree contains "item"*/
            if (data == null) {
                //I don't think a node with null data would ever get to this point, but we check just to be safe
                return false;
            }
            else if (item.compareTo(data) == 0) {
                return true;
            }
            else if (item.compareTo(data) < 0 && left != null) {
                return left.contains(item);
            }
            else if (item.compareTo(data) > 0 && right != null){
                return right.contains(item);
            }
            else {
                return false;
            }
        }

        @Override
        public String toString() {
            /*Recursively adds all nodes to one long string inorder*/
            String result = "";
            if (left != null) {
                result += left.toString();
            }
            //we only add the space once we add actual data to the string
            result += data.toString() + " ";
            if (right != null) {
                result += right.toString();
            }
            return result;
        }

        @Override
        public Iterator<T> iterator() {
            /*Returns a new NodeIterator that has the same fields available to it as the current Node object*/
            return new NodeIterator();
        }

        /* This class is called NodeIterator, but technically it's an iterator for the data within nodes*/
        private class NodeIterator implements Iterator<T>, Serializable {
            Queue<T> queue = new LinkedList<>();
            Iterator<T> iterator;
            private static final long serialVersionUID = 5L;

            NodeIterator() {
                /*Constructor causes the iterator to start directly from the root*/
                addNodeDataToQueue(root);
                //Uses the built-in LinkedList iterator
                iterator = queue.iterator();
            }

            private void addNodeDataToQueue(Node node) {
                /*Recursively traverses tree inorder and adds nodes to the queue that contains the true iterator*/
                if (node.left != null) addNodeDataToQueue(node.left);
                queue.add(node.data);
                if (node.right != null) addNodeDataToQueue(node.right);
            }

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public T next() {
                return iterator.next();
            }
        }
    }
}
