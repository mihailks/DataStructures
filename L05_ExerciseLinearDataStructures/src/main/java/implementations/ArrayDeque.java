package implementations;

import interfaces.Deque;

import java.util.Iterator;

public class ArrayDeque<E> implements Deque<E> {


    public static final int INITIAL_CAPACITY = 7;
    private Object[] elements;
    private int head;
    private int tail;
    private int size;

    public ArrayDeque() {
        this.elements = new Object[INITIAL_CAPACITY];
        int middle = INITIAL_CAPACITY / 2;
        this.head = this.tail = middle;
    }

    @Override
    public void add(E Element) {

        if (this.size == 0) {
            this.elements[head] = elements;
        } else {
            this.elements[++this.tail] = elements; //когато добавяме в края, гледаме къде е средният индекс. Първо го увеличаваме с 1 (местим го на следващият индекс) и после местим tail там и съответното нещо, което искаме да запазим;
        }



        this.size++;
    }

    @Override
    public void offer(E element) {

    }

    @Override
    public void addFirst(E element) {

    }

    @Override
    public void addLast(E element) {

    }

    @Override
    public void push(E element) {

    }

    @Override
    public void insert(int index, E element) {

    }

    @Override
    public void set(int index, E element) {

    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E get(Object object) {
        return null;
    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public E remove(Object object) {
        return null;
    }

    @Override
    public E removeFirst() {
        return null;
    }

    @Override
    public E removeLast() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int capacity() {
        return 0;
    }

    @Override
    public void trimToSize() {

    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }
}
