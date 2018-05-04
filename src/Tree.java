import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

// Custom tree class to meet this project's specific needs
public class Tree<T extends Comparable<T>> implements Serializable, Iterable<T> {
    private Node root;
    private int size;
    private static final long serialVersionUID = 2L;

    // Attempts to insert item into tree
    public void insert(T item) {
        if (size != 0) {
            root.insert(item);
        } else {
            root = new Node(item);
            size = 1;
        }
    }

    // Returns whether the tree contains an item
    public boolean contains(T item) {
        return size != 0 && root.contains(item);
    }

    public int getSize() {
        return size;
    }

    @Override
    // Returns inorder string if the tree has nodes
    public String toString() {
        if (size != 0) {
            return root.toString();
        } else {
            return "";
        }
    }

    @Override
    // Returns the root Node's iterator
    public Iterator<T> iterator() {
        if (size > 0) {
            return root.iterator();
        }
        return Collections.emptyIterator();
    }

    private class Node implements Iterable<T>, Serializable {
        private T data;
        private Node left;
        private Node right;
        private static final long serialVersionUID = 4L;

        // Recreation of default constructor, with values initialized to null for funsies
        Node() {
            data = null;
            left = null;
            right = null;
        }

        // Constructor to set data right away
        Node(T data) {
            this.data = data;
            left = null;
            right = null;
        }

        // Recursively finds the spot (if any) where "item" should be inserted
        void insert(T item) {
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
            //else if the items are equal, we don't want to insert the new one at all, so we do nothing
        }

        // Recursively checks whether the tree contains "item"
        boolean contains(T item) {
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
        // Recursively adds all nodes to one long string inorder
        public String toString() {
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
        // Returns a new NodeIterator that has the same fields available to it as the current Node object
        public Iterator<T> iterator() {
            return new NodeIterator();
        }

        // This class is called NodeIterator, but technically it's an iterator for the data *within* nodes
        private class NodeIterator implements Iterator<T>, Serializable {
            Queue<T> queue = new LinkedList<>();
            Iterator<T> iterator;
            private static final long serialVersionUID = 5L;

            // Constructor causes the iterator to start directly from the root
            NodeIterator() {
                addNodeDataToQueue(root);
                //Uses the built-in LinkedList iterator
                iterator = queue.iterator();
            }

            // Recursively traverses tree inorder and adds nodes to the queue
            private void addNodeDataToQueue(Node node) {
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
