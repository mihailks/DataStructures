package implementations;

import interfaces.AbstractQueue;

import java.util.Iterator;

public class Queue<E> implements AbstractQueue<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    private static class Node<E> {
        private E value;
        private Node<E> next;

        Node(E element) {
            this.value = element;
        }
    }

    public Queue() {
        this.head = null;
        size = 0;
    }

    @Override
    public void offer(E element) {

        Node<E> toInsert = new Node<>(element);

        if (this.isEmpty()) {
            this.head = toInsert;
            this.tail = toInsert;
            this.size++;
            return;
        }
        this.tail.next = toInsert; //на сегашният последен елемент добавяме следващ;
        this.tail = toInsert; // след, като вече си има един следващ, местим запазеният последен.

        this.size++;
    }

    @Override
    public E poll() {
        ensureNonEmpty();

        E elementToReturn = this.head.value;

        if (this.size == 1) {
            this.head = this.tail = null;
            this.size--;
            return elementToReturn;
        }

        Node<E> next = this.head.next;
        this.head.next = null; // на текущият хеад, правим следващият му да е нулл
        this.head = next; //правим текущият елемент да си е следващ

        this.size--;
        return elementToReturn;
    }


    @Override
    public E peek() {
        ensureNonEmpty();
        return this.head.value;
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
                E value = current.value;

                current = current.next;

                return value;
            }
        };
    }

    private void ensureNonEmpty() {
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }
    }
}
