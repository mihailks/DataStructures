import java.util.ArrayDeque;
import java.util.Deque;

public class RedBlackTree<Key extends Comparable<Key>, Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;     // root of the BST

    // BST helper node data type
    private class Node {
        private Key key;           // key
        private Value val;         // associated data
        private Node left, right;  // links to left and right subtrees
        private boolean color;     // color of parent link
        private int size;          // subtree count

        public Node(Key key, Value val, boolean color, int size) {
            this.key = key;
            this.val = val;
            this.color = color;
            this.size = size;
        }
    }

    public RedBlackTree() {
    }

    // is node x red; false if x is null ?
    private boolean isRed(Node x) {
        if (x == null) {
            return false;
        }
        return x.color;
    }

    // number of node in subtree rooted at x; 0 if x is null
    private int size(Node x) {
        if (x == null) {
            return 0;
        }

        return x.size;
    }


    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return this.size(root);
    }

    /**
     * Is this symbol table empty?
     *
     * @return {@code true} if this symbol table is empty and {@code false} otherwise
     */
    public boolean isEmpty() {
        return this.root == null;
    }

    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }
        // iterative version
        Node current = this.root;
        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp < 0) {
                current = current.left; // if the key is less than the current key, we go to the left
            } else if (cmp > 0) {
                current = current.right; // if the key is greater than the current key, we go to the right
            } else {
                return current.val; // if the key is equal to the current key, we return the value
            }
        }
        return null; // if we dont find the key, we return null
    }

    // value associated with the given key in subtree rooted at x; null if no such key
    private Value get(Node x, Key key) {
        return null;
    }

    public boolean contains(Key key) {
        return this.get(key) != null;
    }

    public void put(Key key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException("first argument to put() is null");
        }
        this.root = this.put(this.root, key, val);
        this.root.color = BLACK;
    }

    // insert the key-value pair in the subtree rooted at h
    private Node put(Node h, Key key, Value val) {
        if (h == null) {
            return new Node(key, val, RED, 1);
        }

        int cmp = key.compareTo(h.key);
        if (cmp < 0) {
            h.left = this.put(h.left, key, val); // h in witch i am now, i have to go to the left
            //we do this util we find a leaf. Only then we create a new node
        } else if (cmp > 0) {
            h.right = this.put(h.right, key, val);
        } else {
            h.val = val; // if the key is already in the tree, we just update the value
        }

        if (isRed(h.right) && !isRed(h.left)) {
            h = this.rotateLeft(h);

        }
        if (isRed(h.left) && isRed(h.left.left)) { // we cant have a sequence of two red links
            h = this.rotateRight(h);

        }
        if (isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }
        h.size = this.size(h.left) + this.size(h.right) + 1;
        return h;
    }

    public void deleteMin() {
        if (this.isEmpty()) {
            throw new IllegalArgumentException("BST is empty");
        }
        // if we have to delete the root
        if (this.root.left == null) {
            this.root = null;
            return;
        }
        this.root = this.deleteMin(this.root);
    }

    // delete the key-value pair with the minimum key rooted at h
    private Node deleteMin(Node h) {
        if (h.left == null) {
            return null;
        }

        if (!isRed(h.left) && !isRed(h.left.left)) {
            h = moveRedLeft(h);
        }

        h.left = this.deleteMin(h.left);
        return balance(h);
    }


    public void deleteMax() {
        if (this.isEmpty()) {
            throw new IllegalArgumentException("BST is empty");
        }
        // if we have to delete the root
        if (this.root.right == null) {
            this.root = null;
            return;
        }
        this.root = this.deleteMax(this.root);

    }

    // delete the key-value pair with the maximum key rooted at h
    private Node deleteMax(Node h) {
        if (isRed(h.left)) {
            h = rotateRight(h);
        }
        if (h.right == null) { //bottom of the recursion
            return null;
        }
        if (!isRed(h.right) && !isRed(h.right.right)) {
            h = moveRedRight(h);
        }

        h.right = deleteMax(h.right);

        return balance(h);
    }

    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }
        if (!this.contains(key)) {
            return;
        }
        // if both children of root are black, set root to red
        if (!isRed(this.root.left) && !isRed(this.root.right)) {
            this.root.color = RED;
        }
        this.root = this.delete(this.root, key);
        if (!this.isEmpty()) {
            this.root.color = BLACK;
        }
    }

    // delete the key-value pair with the given key rooted at h
    private Node delete(Node h, Key key) {
        if (key.compareTo(h.key) < 0) {
            if (!isRed(h.left) && !isRed(h.left.left)) {
                h = moveRedLeft(h);
            }
            h.left = this.delete(h.left, key);
        } else {
            if (isRed(h.left)) {
                h = rotateRight(h);
            }
            if (key.compareTo(h.key) == 0 && (h.right == null)) {
                return null;
            }
            if (!isRed(h.right) && !isRed(h.right.left)) {
                h = moveRedRight(h);
            }
            if (key.compareTo(h.key) == 0) {
                Node temp = this.min(h.right);
                h.key = temp.key;
                h.val = temp.val;
                h.right = this.deleteMin(h.right);
            } else {
                h.right = this.delete(h.right, key);
            }
        }
        return balance(h);

    }

    private Node rotateRight(Node h) {
        Node temp = h.left;
        h.left = temp.right;
        temp.right = h;

        temp.color = temp.right.color;
        temp.right.color = RED;
        temp.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;

        return temp;
    }

    // make a right-leaning link lean to the left
    private Node rotateLeft(Node h) { // we make it left child to it`s right node
        Node temp = h.right;
        h.right = temp.left;
        temp.left = h;

        temp.color = temp.left.color;
        temp.left.color = RED;
        temp.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;

        return temp;
    }

    // flip the colors of a node and its two children
    private void flipColors(Node h) {
        h.color = !h.color;  // flip color, but because it is boolean, we just change it to the opposite
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    // Assuming that h is red and both h.left and h.left.left
    // are black, make h.left or one of its children red.
    private Node moveRedLeft(Node h) {
        flipColors(h);
        if (isRed(h.right.left)) { // if the left child of the right child is red, we rotate
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    // Assuming that h is red and both h.right and h.right.left
    // are black, make h.right or one of its children red.
    private Node moveRedRight(Node h) {
        flipColors(h);
        if (isRed(h.left.left)) { // if the left child of the right child is red, we rotate
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    // restore red-black tree invariant
    private Node balance(Node h) {
        // black root, black leafs and same number of black links in all routes from root to leaf
        if (isRed(h.right)) {
            h = rotateLeft(h);
        }
        if (isRed(h.left) && isRed(h.left.left)) { // we cant have a sequence of two red links
            h = rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }
        h.size = this.size(h.left) + this.size(h.right) + 1;
        return h;
    }

    public int height() {
        int height = this.height(root);
        return height;
    }

    private int height(Node x) {
        if (x == null) {
            return -1;
        }
        return Math.max(height(x.left), height(x.right)) + 1;
    }

    public Key min() {
        Node min = this.min(this.root);
        return min != null ? min.key : null;
    }

    // the smallest key in subtree rooted at x; null if no such key
    private Node min(Node x) {
        while (x != null) {
            if (x.left == null) {
                return x;
            }
            x = x.left;
        }
        return null;
    }

    public Key max() {
        Node max = this.max(this.root);
        if (max.key == null) {
            return null;
        }
        return max.key;
    }

    // the largest key in the subtree rooted at x; null if no such key
    private Node max(Node x) {
        if (x.right == null) {
            return x;
        }
        return max(x.right);
    }

    public Key floor(Key key) {
        Node floor = this.floor(this.root, key);
        if (floor == null) {
            throw new IllegalArgumentException("argument to floor() is null");
        }
        return floor.key;
    }

    // the largest key in the subtree rooted at x less than or equal to the given key
    private Node floor(Node x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp == 0) { // if the key is equal to the current key, we return the value
            return x;
        }
        if (cmp < 0) { // if the key is less than the current key, we go to the left
            return floor(x.left, key);
        }
        Node temp = floor(x.right, key); // if the key is greater than the current key, we go to the right
        if (temp != null) {
            return temp;
        } else {
            return x;
        }
    }

    public Key ceiling(Key key) {
        Node ceiling = this.ceiling(this.root, key);
        if (ceiling == null) {
            throw new IllegalArgumentException("argument to ceiling() is null");
        }
        return ceiling.key;
    }

    // the smallest key in the subtree rooted at x greater than or equal to the given key
    private Node ceiling(Node x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp == 0) { // if the key is equal to the current key, we return the value
            return x;
        }
        if (cmp > 0) { // if the key is greater than the current key, we go to the right
            return ceiling(x.right, key);
        }
        Node temp = ceiling(x.left, key); // if the key is less than the current key, we go to the left
        if (temp != null) {
            return temp;
        } else {
            return x;
        }
    }

    public Key select(int rank) {
        if (rank < 0 || rank >= this.size()) {
            throw new IllegalArgumentException("argument to select() is invalid: " + rank);
        }
        return select(this.root, rank);
    }

    // Return key in BST rooted at x of given rank.
    // Precondition: rank is in legal range.
    private Key select(Node x, int rank) {
        if (x == null) {
            return null;
        }
        int leftSize = this.size(x.left);
        if (leftSize > rank) {
            return select(x.left, rank);
        } else if (leftSize < rank) {
            return select(x.right, rank - leftSize - 1);
        } else {
            return x.key;
        }
    }

    public int rank(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to rank() is null");
        }
        return rank(key, this.root);
    }

    // number of keys less than key in the subtree rooted at x
    private int rank(Key key, Node x) {
        if (x == null) {
            return 0;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) { // if the key is less than the current key, we go to the left
            return rank(key, x.left);
        } else if (cmp > 0) { // if the key is greater than the current key, we go to the right
            return 1 + this.size(x.left) + rank(key, x.right);
        } else {
            return this.size(x.left);
        }
    }

    public Iterable<Key> keys() {

        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Deque<Key> deque = new ArrayDeque<>();
        keys(this.root, deque, lo, hi);
        return deque;
    }

    // add the keys between lo and hi in the subtree rooted at x
    // to the queue
    private void keys(Node x, Deque<Key> queue, Key lo, Key hi) {
        if (x == null) {
            return;
        }
        int cmpLo = lo.compareTo(x.key);
        int cmpHi = hi.compareTo(x.key);
        if (cmpLo < 0) { // if the key is less than the current key, we go to the left
            keys(x.left, queue, lo, hi);
        }
        if (cmpLo <= 0 && cmpHi >= 0) { // if the key is between the lo and hi, we add it to the queue
            queue.add(x.key);
        }
        if (cmpHi > 0) { // if the key is greater than the current key, we go to the right
            keys(x.right, queue, lo, hi);
        }
    }

    public int size(Key lo, Key hi) {
        return 0;
    }

    private boolean check() {
        return false;
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private boolean isBST() {
        return false;
    }

    // is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    private boolean isBST(Node x, Key min, Key max) {
        return false;
    }

    // are the size fields correct?
    private boolean isSizeConsistent() {
        return false;
    }

    private boolean isSizeConsistent(Node x) {
        return false;
    }

    // check that ranks are consistent
    private boolean isRankConsistent() {
        return false;
    }

    // Does the tree have no red right links, and at most one (left)
    // red links in a row on any path?
    private boolean isTwoThree() {
        return false;
    }

    private boolean isTwoThree(Node x) {
        return false;
    }

    // do all paths from root to leaf have same number of black edges?
    private boolean isBalanced() {
        return false;
    }

    // does every path from the root to a leaf have the given number of black links?
    private boolean isBalanced(Node x, int black) {
        return false;
    }
}
