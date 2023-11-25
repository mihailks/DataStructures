import java.util.function.Consumer;

public class AVL<T extends Comparable<T>> {

    private Node<T> root;

    public Node<T> getRoot() {
        return this.root;
    }

    public boolean contains(T item) {
        Node<T> node = this.search(this.root, item);
        return node != null;
    }

    public void insert(T item) {
        this.root = this.insert(this.root, item);
    }

    public void eachInOrder(Consumer<T> consumer) {
        this.eachInOrder(this.root, consumer);
    }

    private void eachInOrder(Node<T> node, Consumer<T> action) {
        if (node == null) {
            return;
        }

        this.eachInOrder(node.left, action);
        action.accept(node.value);
        this.eachInOrder(node.right, action);
    }

    private Node<T> insert(Node<T> node, T item) {
        if (node == null) {
            return new Node<>(item);
        }

        int cmp = item.compareTo(node.value);
        if (cmp < 0) {
            node.left = this.insert(node.left, item);
        } else if (cmp > 0) {
            node.right = this.insert(node.right, item);
        }

        updateHeight(node);
        node = balance(node);


        return node;
    }



    private Node<T> search(Node<T> node, T item) {
        if (node == null) {
            return null;
        }

        int cmp = item.compareTo(node.value);
        if (cmp < 0) {
            return search(node.left, item);
        } else if (cmp > 0) {
            return search(node.right, item);
        }

        return node;
    }

    private void updateHeight(Node<T> node) { //update height of node
        node.height = Math.max(height(node.left), height(node.right)) + 1; // we check the height of left and right node and add 1.
        // If we have only one note on right for example, the height will be 1 and on the left will be 0(no children on the left),
        // and we add one. because this note have at least one child.
    }

    private int height(Node<T> node) { //get height of node
        if (node == null) { // 0 because we start from 0 height if we don`t have children
            return 0;
        }

        return node.height;
    }

    private Node<T> rotateRight(Node<T> x){ // x is the name; Check the presentation
        Node<T> temp = x.left;
        x.left = temp.right; // x.left we already have it as temp "x.left.right"
        temp.right = x;

        updateHeight(x);
        updateHeight(temp);

        return temp;
    }

    private Node<T> rotateLeft(Node<T> x){
        Node<T> temp = x.right;
        x.right = temp.left;
        temp.left = x; // here we add the children, that have nothing to do with the rotate

        updateHeight(x);
        updateHeight(temp);

        return temp;
    }


    private Node<T> balance(Node<T> node) {
//        int balance = getBalance(node); //get balance of node
//
//        if (balance > 1) { //if balance is more than 1, we have to rotate to the right
//            if (getBalance(node.left) < 0) { //if balance of left node is less than 0, we have to rotate to the left
//                node.left = rotateLeft(node.left); //rotate to the left
//            }
//
//            node = rotateRight(node); //rotate to the right
//        } else if (balance < -1) { //if balance is less than -1, we have to rotate to the left
//            if (getBalance(node.right) > 0) { //if balance of right node is more than 0, we have to rotate to the right
//                node.right = rotateRight(node.right); //rotate to the right
//            }
//
//            node = rotateLeft(node); //rotate to the left
//        }

        int balanced = height(node.left) - height(node.right); // по формулата си проверяваме дали сме си балансирани

        if(balanced > )



        return null;
    }
}












