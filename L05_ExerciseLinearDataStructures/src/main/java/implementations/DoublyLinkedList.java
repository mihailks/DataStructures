package implementations;

import interfaces.LinkedList;

import java.util.Iterator;

public class DoublyLinkedList<E> implements LinkedList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    private static class Node<E> {
        private E value;
        private Node<E> next;
        private Node<E> previous;

        public Node(E value) {
            this.value = value;
        }
    }

    public DoublyLinkedList() {
    }

    @Override
    public void addFirst(E element) {
        Node<E> newNode = new Node<>(element);
        if (this.head == null) {
            this.head = this.tail = newNode;
        } else {
            newNode.next = this.head;
            this.head.previous = newNode;
            this.head = newNode;
        }
        this.size++;
    }

    @Override
    public void addLast(E element) {
        if (this.size == 0) {
            this.addFirst(element); //тук пак си знаем, че като е празен листът, хеад-а и тайл-а са един и същи елемент и в него next и previous са null;
            // и дали ще направим първият хеад или теил няма значение
        } else {
            Node<E> newNode = new Node<>(element);
            newNode.previous = this.tail;
            this.tail.next = newNode;
            this.tail = newNode;
            this.size++;
        }
    }

    @Override
    public E removeFirst() {
        ensureNotEmpty();
        E element = this.head.value;
        if (this.size == 1) {
            this.head = this.tail = null;
        } else {
            Node<E> newHead = this.head.next;
            newHead.previous = null;
            this.head.next = null;
            this.head = newHead;
        }
        this.size--;
        return element;
    }

    private void ensureNotEmpty() {
        if (this.size == 0) {
            throw new IllegalStateException("Illegal remove for empty LinkedList");
        }
    }

    @Override
    public E removeLast() {
        ensureNotEmpty();
        if (this.size == 1) {
            return removeFirst();
        }
        E value = this.tail.value;

//        this.tail.previous.next = null;

        Node<E> currentTail = this.tail;
        this.tail = this.tail.previous;
        this.tail.next = null;
        currentTail.previous=null; //махаме и референцията на премахнатият елемент, към неговият предишен, който е бил, за да не оставят разни висящи рефенции в паметта;

        this.size--;

        return value;
    }

    @Override
    public E getFirst() {
        ensureNotEmpty();
        return this.head.value;
    }

    @Override
    public E getLast() {
        ensureNotEmpty();

        return this.tail.value;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }


    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                E element = current.value;
                current = current.next;
                return element;
            }
        };
    }
}
