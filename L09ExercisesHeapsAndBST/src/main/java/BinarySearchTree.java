import solutions.BinaryTree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.function.Consumer;

import java.util.List;

public class BinarySearchTree<E extends Comparable<E>> {
    private Node<E> root;

    public BinarySearchTree() {
    }

    public BinarySearchTree(E element) {
        this.root = new Node<>(element);
    }

    public BinarySearchTree(Node<E> otherRoot) {
        this.root = new Node<>(otherRoot);
    }

    public static class Node<E> {
        private E value;
        private Node<E> leftChild;
        private Node<E> rightChild;
        private int count;

        public Node(E value) {
            this.value = value;
            count = 1;
        }

        public Node(Node<E> other) {
            this.value = other.value;
            this.count = other.count;

            if (other.getLeft() != null) {
                this.leftChild = new Node<>(other.getLeft());
            }
            if (other.getRight() != null) {
                this.rightChild = new Node<>(other.getRight());
            }
        }

        public Node<E> getLeft() {
            return this.leftChild;
        }

        public Node<E> getRight() {
            return this.rightChild;
        }

        public E getValue() {
            return this.value;
        }

        public int getCount() {
            return this.count;
        }
    }

    public void eachInOrder(Consumer<E> consumer) {
        nodeInorder(this.root, consumer);
    }

    private void nodeInorder(Node<E> node, Consumer<E> consumer) {
        if (node == null) {
            return;
        }

        nodeInorder(node.getLeft(), consumer);
        consumer.accept(node.getValue());
        nodeInorder(node.getRight(), consumer);

    }

    public Node<E> getRoot() {
        return this.root;
    }

    public void insert(E element) {
        if (this.root == null) {
            this.root = new Node<>(element);
        } else {
            insertInto(this.root, element);
        }
    }

    private void insertInto(Node<E> node, E element) {

        if (isGreater(element, node)) {
            if (node.getRight() == null) {
                node.rightChild = new Node<>(element);
            } else {
                insertInto(node.getRight(), element);
            }

        } else if (isLess(node, element)) {
            if (node.getLeft() == null) {
                node.leftChild = new Node<>(element);
            } else {
                insertInto(node.getLeft(), element);
            }
        }
        node.count++;
    }


    public boolean contains(E element) {
        // for searchMethod
//        return containsNode(this.root,element)!=null;


        //contains recursive BST
//        return containsNode(this.root, element);
        //iterative  recursive BST
        Node<E> current = this.root;
        while (current != null) {
            if (isGreater(element, current)) {
                current = current.rightChild;
            } else if (isLess(current, element)) {
                current = current.leftChild;
            } else if (isEqual(element, current)) {
                return true;
            }
        }
        return false;
    }

//    //contains recursive BST
//    private boolean containsNode(Node<E> node, E element) {
//        if (node == null) {
//            return false;
//        }
//        if (isEqual(element, node)) {
//            return true;
//        } else if (isGreater(element, node)) {
//            return containsNode(node.getRight(), element);
//        }
//
//        return containsNode(node.getLeft(), element);
//    }


    //search recursive BST
    private Node<E> containsNode(Node<E> node, E element) {
        if (node == null) {
            return null;
        }
        if (isEqual(element, node)) {
            return node;
        } else if (isGreater(element, node)) {
            return containsNode(node.getRight(), element);
        }

        return containsNode(node.getLeft(), element);
    }


    public BinarySearchTree<E> search(E element) {
        Node<E> found = containsNode(this.root, element);
        return found == null ? null : new BinarySearchTree<>(found);
    }

    public List<E> range(E lower, E upper) {
        List<E> result = new ArrayList<>();

        if (this.root == null) {
            return result;
        }

        Deque<Node<E>> queue = new ArrayDeque<>();

        queue.offer(this.root);

        while (!queue.isEmpty()) {
            Node<E> current = queue.poll();

            if (current.getLeft() != null) {
                queue.offer(current.getLeft());
            }
            if (current.getRight() != null) {
                queue.offer(current.getRight());
            }

            if (isLess(current, lower) && isGreater(upper, current)) {
                result.add(current.getValue());
            } else if (isEqual(lower, current) || isEqual(upper, current)) {
                result.add(current.getValue());
            }

        }
        return result;
    }

    public void deleteMin() {
        if (this.root == null) {
            throw new IllegalStateException();
        }

        if (this.root.getLeft() == null) {
            this.root = this.root.getRight();
            return;
        }

        Node<E> current = this.root;
        Node<E> prev = this.root;

        while (current.getLeft() != null) {
            current.count--;
            prev = current;
            current = current.getLeft();
        }

        //ако го направим така, може да пропуснем това с prev
        //просто правим 2 пъти .getLeft().getLeft(), така знаем, че по-долният няма да има деца и го махаме от текущият
//        while (current.getLeft().getLeft() != null) {
//            current = current.getLeft();
//        }
        current.count--;
        prev.leftChild = current.getRight();

    }

    public void deleteMax() {
        if (this.root == null) {
            throw new IllegalStateException();
        }


        if (this.root.getRight() == null) {
            this.root = this.root.getLeft();
            return;
        }

        Node<E> current = this.root;

        while (current.getRight().getRight() != null) {
            current.count--;
            current = current.getRight();
        }
        current.count--;
        current.rightChild = current.getRight().getLeft();


    }

    public int count() {
        return this.root == null ? 0 : this.root.count;
    }

    public int rank(E element) {
        return nodeRank(this.root, element);
    }

    private int nodeRank(Node<E> node, E element) {
        if (node == null) {
            return 0;
        }

        if (isLess(node, element)) {
            return nodeRank(node.getLeft(), element);
        } else if (isEqual(element, node)) {
            return getNodeCount(node.getLeft());
        } else {
            return getNodeCount(node.getLeft()) + 1 + nodeRank(node.getRight(), element);
        }
    }

    private int getNodeCount(Node<E> node) {
        return node == null ? 0 : node.getCount();
    }

    public E floor(E element) {
        if (this.root == null) {
            return null;
        }

        Node<E> current = this.root;
        Node<E> nearestSmaller = null;
        while (current != null) {

            if (isGreater(element, current)) {
                nearestSmaller = current;
                current = current.getRight();
            } else if (isLess(current, element)) {
                current = current.getLeft();
            } else {
                Node<E> left = current.getLeft();
                if (left != null && nearestSmaller != null) {
                    nearestSmaller = isGreater(left.getValue(), nearestSmaller) ? left : nearestSmaller;
                } else if (nearestSmaller == null) {
                    nearestSmaller = left;
                }
                break;
            }
        }
        return nearestSmaller == null ? null : nearestSmaller.getValue();
    }

    public E ceil(E element) {
        if (this.root == null) {
            return null;
        }

        Node<E> current = this.root;
        Node<E> nearestBigger = null;

        while (current != null) {
            if (isLess(current, element)) {
                nearestBigger = current;
                current = current.getLeft();
            } else if (isGreater(element, current)) {
                current = current.getRight();
            } else {
                Node<E> right = current.getRight();
                if (right != null && nearestBigger != null) {
                    nearestBigger = isLess(nearestBigger, right.getValue()) ? right : nearestBigger;
                } else if (nearestBigger == null){
                    nearestBigger = right;
                }
                break;
            }

        }

        return nearestBigger == null ? null : nearestBigger.getValue();
    }

    private boolean isLess(Node<E> node, E element) {
        return element.compareTo(node.getValue()) < 0;
    }

    private boolean isGreater(E element, Node<E> node) {
        return element.compareTo(node.getValue()) > 0;
    }

    private boolean isEqual(E element, Node<E> node) {
        return element.compareTo(node.getValue()) == 0;
    }
}
