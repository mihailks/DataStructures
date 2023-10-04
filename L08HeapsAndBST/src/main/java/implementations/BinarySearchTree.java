package implementations;

import interfaces.AbstractBinarySearchTree;

public class BinarySearchTree<E extends Comparable<E>> implements AbstractBinarySearchTree<E> {

    private Node<E> root;
    private Node<E> leftChild;
    private Node<E> rightChild;

    public BinarySearchTree() {
    }

    public BinarySearchTree(Node<E> root) { //използваме този конструктор за да можем да върнем копие, а не рефенция
        this.copy(root);
    }

    private void copy(Node<E> node) {
        if (node != null) {
            this.insert(node.value);
            this.copy(node.leftChild);
            this.copy(node.rightChild);
        }
    }

    @Override
    public void insert(E element) {

        Node<E> newNode = new Node<>(element);

        if (this.getRoot() == null) {
            this.root = newNode;
        } else {
            Node<E> current = this.root; //започваме търсенето от руут
            Node<E> prevNode = this.root; //взимаме си и предишният, за да може, като стигнем до, къде трябва да го закачаме, трябва да знаем, кой ще му е родителят

            while (current != null) {
                prevNode = current;
                if (isLess(element, current.value)) {
                    current = current.leftChild;
                } else if (isGreater(element, current.value)) {
                    current = current.rightChild;
                } else if (areEqual(element, current.value)) {
                    return; // ако са равни, нищо не правим, вече е вътре новия
                }
            }

            //, къде трябва да го закачаме, трябва да знаем, кой ще му е родителят
            current = newNode;
            if (isLess(element, prevNode.value)) {
                prevNode.leftChild = newNode;
            } else if (isGreater(element, prevNode.value)) {
                prevNode.rightChild = newNode;
            }

        }

    }

    private boolean isLess(E first, E second) {
        return first.compareTo(second) < 0;
    }

    private boolean isGreater(E first, E second) {
        return first.compareTo(second) > 0;
    }

    private boolean areEqual(E first, E second) {
        return first.compareTo(second) == 0;
    }

    @Override
    public boolean contains(E element) {
        Node<E> current = this.root;

        while (current != null) {
            if (isLess(element, current.value)) {
                current = current.leftChild;
            } else if (isGreater(element, current.value)) {
                current = current.rightChild;
            } else if (areEqual(element, current.value)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public AbstractBinarySearchTree<E> search(E element) {

        AbstractBinarySearchTree<E> result = new BinarySearchTree<>();
        Node<E> current = this.root;

        while (current != null) {
            if (isLess(element, current.value)) {
                current = current.leftChild;
            } else if (isGreater(element, current.value)) {
                current = current.rightChild;
            } else if (areEqual(element, current.value)) {
                return new BinarySearchTree<>(current);
            }
        }

        return result;
    }

    @Override
    public Node<E> getRoot() {
        return this.root;
    }

    @Override
    public Node<E> getLeft() {
        return this.leftChild;
    }

    @Override
    public Node<E> getRight() {
        return this.rightChild;
    }

    @Override
    public E getValue() {
        return this.root.value;
    }
}
