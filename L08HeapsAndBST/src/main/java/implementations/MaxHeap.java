package implementations;

import interfaces.Heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaxHeap<E extends Comparable<E>> implements Heap<E> {

    private List<E> elements;

    public MaxHeap() {
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

    private boolean isLess(int childIndex, int parentIndex) {
        return this.elements.get(childIndex).compareTo(this.elements.get(parentIndex)) > 0; //сравняваме си новия и родителя, и aко родителя е по-малък им сменяме местата
    }

    private int getParentIndex(int index) {
        return (index - 1) / 2; // формулата си е от стандартна за намиране на родител
    }

    @Override
    public E peek() {
        if (this.size()==0){
            throw new IllegalStateException();
        }
        return this.elements.get(0);
    }
}
