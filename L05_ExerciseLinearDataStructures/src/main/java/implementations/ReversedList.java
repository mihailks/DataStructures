package implementations;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ReversedList<E> implements interfaces.ReversedList<E> {
    private final int DEFAULT_CAPACITY = 2;
    private Object[] elements;
    private int head;
    private int size;

    public ReversedList() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    @Override
    public void add(E element) {
        if (size == 0) {
            elements[this.head] = element;
            size++;
            return;
        } else if (this.head == 0) {
            this.grow();
        }
        elements[--this.head] = element;
        size++;
    }

    private void grow() {

        Object[] newElements = new Object[elements.length * 2];

        int j = newElements.length - 1;

        for (int i = this.elements.length - 1; i >= 0; i--) {
            newElements[j--] = this.elements[i];
        }

        this.elements = newElements;
        this.head = this.elements.length / 2;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int capacity() {
        return this.elements.length;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        if (index < 0 || index > this.size - 1) {
            throw new IndexOutOfBoundsException(index);
        }
        return (E) elements[this.head + index];
    }

    @Override
    public E removeAt(int index) {
        E element = this.get(index);
        for (int i = this.head + index; i > this.head; i--) {
            this.elements[i] = this.elements[i - 1];
        }
        this.elements[this.head++] = null;
        size--;

        return element;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = head;

            @Override
            public boolean hasNext() {
                return index < elements.length;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (E) elements[index++];
            }
        };
    }
}
