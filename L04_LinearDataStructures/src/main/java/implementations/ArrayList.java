package implementations;

import interfaces.List;

import java.util.Arrays;
import java.util.Iterator;

public class ArrayList<E> implements List<E> {
    public static final int INITIAL_SIZE = 4;
    private Object[] elements;
    private int size = 0; //current number of elements
    private int capacity = INITIAL_SIZE; //current size (колко му е цялостния размер)

    public ArrayList() {
        this.elements = new Object[INITIAL_SIZE];
    }

    @Override
    public boolean add(E element) {

        if (this.size == this.capacity) {
            grow();
        }

        //започваме от първи елемент и след, като масива е празен направо следим индекса с променливата за броя на елементите му, които има в момента в него.
        // Когато Size==0 нямаме елементи и добавяме на нулева позиция
        this.elements[this.size] = element; //може направо и тук да го добавим, като: this.elements[this.size++] = element;
        this.size++;

        return true;
    }

    @Override
    public boolean add(int index, E element) {
        if (!validIndex(index)) {
            return false;
        }

        shiftRight(index);
        this.elements[index] = element;
        this.size++;

        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        validateAndTrow(index);
        return (E) this.elements[index];
    }

    @SuppressWarnings("unchecked")
    @Override
    public E set(int index, E element) {

        validateAndTrow(index);

        Object currentElementInIndexPlace = this.elements[index];
        this.elements[index] = element;
        return (E) currentElementInIndexPlace;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E remove(int index) {

        validateAndTrow(index);

        Object currentElementInIndexPlace = this.elements[index];

        shiftLeft(index);
        this.size--;

        shrinkIfNeeded();

        return (E) currentElementInIndexPlace;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int indexOf(E element) {
        for (int i = 0; i < this.size; i++) {
            if (this.elements[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(E element) {
        return this.indexOf(element) != -1;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < size();
            }

            @Override
            public E next() {
                //вика get от index и после му увеличава стойността
                return get(index++);
            }
        };
    }

    private void grow() {

        this.capacity *= 2;
        Object[] temp = new Object[this.capacity];

        for (int i = 0; i < this.elements.length; i++) {
            temp[i] = this.elements[i];
        }
        //Копираме си обратно масива във стария
        this.elements = temp;

    }

    //цепим масива до индекса и местим всичко надясно
    private void shiftRight(int index) {
        for (int i = this.size - 1; i >= index; i--) {
            this.elements[i + 1] = this.elements[i];
        }
    }

    private void shiftLeft(int index) {
        for (int i = index; i < this.size - 1; i++) {
            this.elements[i] = this.elements[i + 1];
        }
    }

    private boolean validIndex(int index) {
        return index >= 0 && index < this.size;
    }

    private void validateAndTrow(int index) {
        if (!validIndex(index)) {
            throw new IndexOutOfBoundsException("Index " + index + " is not in ArrayList with size " + this.size);
        }
    }
    private void shrinkIfNeeded() {
        if (this.size > this.capacity / 3) {
            return;
        }
        this.capacity /= 2;
        this.elements = Arrays.copyOf(this.elements, this.capacity);
    }


}


















