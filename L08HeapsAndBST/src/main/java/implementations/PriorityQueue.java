package implementations;

import interfaces.AbstractQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PriorityQueue<E extends Comparable<E>> implements AbstractQueue<E> {

    private List<E> elements;

    public PriorityQueue() {
        this.elements = new ArrayList<>();
    }

    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    public void add(E element) {
        this.elements.add(element);
        this.heapifyUp(this.size() - 1); //тръгваме от последният индекс и караме, към първия
    }

    private void heapifyUp(int index) {//трябва да си намерим индекса на родителя.
        while (index > 0 && isLess(index, getParentIndex(index))) {
            Collections.swap(this.elements, index, getParentIndex(index));
            index = getParentIndex(index);
        }
    }

    private boolean isLess(int first, int second) {
        return this.elements.get(first).compareTo(this.elements.get(second)) > 0; //сравняваме си новия и родителя, и aко родителя е по-малък им сменяме местата
    }

    private int getParentIndex(int index) {
        return (index - 1) / 2; // формулата си е от стандартна за намиране на родител
    }

    @Override
    public E peek() {
        ensureNotEmpty();
        return this.elements.get(0);
    }

    @Override
    public E poll() { // сменяме местата на новия елемент и на този най-отгоре, после този стария го махаме
        ensureNotEmpty();
        E returnedValue = elements.get(0);
        Collections.swap(this.elements, 0, this.size() - 1);

        this.elements.remove(this.size() - 1);

        this.heapifyDown(0);

        return returnedValue;
    }

    private int getLeftChildIndex(int index) {
        return 2 * index + 1;
    }

    private int getRightChildIndex(int index) {
        return 2 * index + 2;
    }

    private void heapifyDown(int index) { // парент ноуда
        //започвам от лявото дете и проверявам дали го има и после дали е по-малко от родителя.
        while (getLeftChildIndex(index) < this.size() && isLess(index, getLeftChildIndex(index))) {
            // приемам, че лявото ще е това, с което трябва да слапна родителя
            int chielNode = getLeftChildIndex(index); //трябва все пак да си запазим индекса на детето, до което сме стигнали

            //проверявам дали все пак няма дясно дете и ако има и е по-голямо от лявото. Ако е вярно се захващам с него
            int rightChildIndex = getRightChildIndex(index); // връща индекса на дясното дете
            if (rightChildIndex < this.size() && isLess(chielNode, rightChildIndex)) {
                chielNode = rightChildIndex;
            }
            //след, като съм определил, кое дете ще сменям, тук извършвам смяната
            Collections.swap(this.elements, chielNode, index);
            index = chielNode;
        }

    }

    private void ensureNotEmpty() {
        if (this.size() == 0) {
            throw new IllegalStateException();
        }
    }
}
