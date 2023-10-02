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
    public void add(E element) {
        addLast(element);
    }

    @Override
    public void offer(E element) {
        addLast(element);
    }

    @Override
    public void addFirst(E element) {
        if (this.size == 0) {
            this.addLast(element); // просто преизползваме if-a на предния метод
        } else {
            if (this.head == 0) {
                this.elements = grow();
            }
            this.elements[--this.head] = element;
            this.size++;
        }

    }

    @Override
    public void addLast(E element) {
        if (this.size == 0) {
            this.head = this.tail = capacity() / 2;
            this.elements[this.head] = element;

        } else {
            if (this.tail == this.elements.length - 1) {
                this.elements = grow();
            }
            this.elements[++this.tail] = element; //когато добавяме в края, гледаме къде е средният индекс. Първо го увеличаваме с 1 (местим го на следващият индекс) и после местим tail там и съответното нещо, което искаме да запазим;
        }

        this.size++;
    }

    @Override
    public void push(E element) {
        addFirst(element);
    }

    @Override
    public void insert(int index, E element) {

        int realIndex = this.head + index;
        this.ensureIndex(realIndex);

        if (realIndex - this.head < this.tail - realIndex) { // проверка, в до кой край е по-близо индекса, за да знаем, коя страна ще шифтваме
            insertAndsSiftLeft(realIndex, element);
        } else {
            insertAndSiftRight(realIndex, element);
        }

    }

    private void insertAndSiftRight(int index, E element) {
        E lastElement = this.getAt(this.tail);
        for (int i = this.tail; i > index; i--) {
            this.elements[i] = this.elements[i - 1];
        }
        this.elements[index] = element;
        this.addLast(lastElement);       // запазваме си последният, защото като започнем да ги копираме от зад на пред, при първото копиране ще го изгубим
    }

    private void insertAndsSiftLeft(int index, E element) {
        E firstElement = this.getAt(this.head);
        for (int i = this.head; i <= index; i++) {
            this.elements[i] = this.elements[i + 1];
        }
        this.elements[index] = element;
        this.addFirst(firstElement);
    }

    @Override
    public void set(int index, E element) {
        int realIndex = this.head + index;
        ensureIndex(realIndex);
        this.elements[realIndex] = element;
    }

    @Override
    public E peek() {
        if (!this.isEmpty()) {
            return this.getAt(this.head); //за да избегнем навсякъде кастовете от object -> E правим един метод
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private E getAt(int index) {
        return (E) this.elements[index];
    }

    @Override
    public E poll() {
        return removeFirst();
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public E get(int index) {
        int realIndex = this.head + index;
        this.ensureIndex(realIndex);
        return this.getAt(realIndex);
    }

    @Override
    public E get(Object object) {
        if (isEmpty()) {
            return null;
        }
        for (int i = this.head; i <= this.tail; i++) {
            if (this.elements[i].equals(object)) {
                return this.getAt(i);
            }
        }
        return null;
    }

    @Override
    public E remove(int index) {    // реално индекса, който идва от външното викане на метода не е истинският, защото първите са ни празни
        int realIndex = this.head + index;
        ensureIndex(realIndex);

        E elementToDelete = getAt(realIndex);

        for (int j = realIndex; j < this.tail; j++) {
            this.elements[j] = this.elements[j + 1];
        }
        this.removeLast();

        return elementToDelete;
    }

    private void ensureIndex(int realIndex) {
        if (realIndex < this.head || realIndex > this.tail) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public E remove(Object object) {
        if (isEmpty()) {
            return null;
        }
        for (int i = this.head; i <= this.tail; i++) {
            if (this.elements[i].equals(object)) {
                E element = this.getAt(i);
                this.elements[i] = null;

                for (int j = i; j < this.tail; j++) {
                    this.elements[j] = this.elements[j + 1];
                }
                this.removeLast();
                return element;
            }
        }
        return null;
    }

    @Override
    public E removeFirst() {
        if (!this.isEmpty()) {
            E elementToReturn = this.getAt(this.head);
            this.elements[this.head] = null;
            this.head++;
            this.size--;
            return elementToReturn;
        } else {
            return null;
        }
    }

    @Override
    public E removeLast() {
        if (!this.isEmpty()) {
            E elementToReturn = this.getAt(this.tail);
            elements[this.tail] = null;
            this.tail--;
            this.size--;
            return elementToReturn;
        } else {
            return null;
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int capacity() {
        return this.elements.length;
    }

    @Override
    public void trimToSize() {
        Object[] newElements = new Object[size];
        int index = 0;
        for (int i = this.head; i <= this.tail; i++) {
            newElements[index++] = this.elements[i];
        }
        this.elements = newElements;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {

        return new Iterator<>() {

            private int index = head;

            @Override
            public boolean hasNext() {
                return index <= tail; // докато не си стигнал края -> има следващ
            }

            @Override
            public E next() {
                return getAt(index++); //достъпи елементите дай ми този, на който си и се премести на следващият "++"
            }
        };
    }

    private Object[] grow() {
        int newCapacity = this.elements.length * 2 + 1;// понеже нечетно по 2 дава винаги четно, събираме с 1 за да може пак да си имаме точно фиксиран среден елемент

        Object[] temp = new Object[newCapacity];

        int middle = newCapacity / 2;           //намираме центъра на новия дек

        int begin = middle - this.size / 2;     //намираме, кой индекс в новия ще започнем да записваме елементите

        int index = this.head;                  //взимаме индекса от който ни започват елементите в началния дек

        for (int i = begin; index <= this.tail; i++) { // караме до края на началния дек
            temp[i] = this.elements[index++];
        }

        this.head = begin; //местим хеда на индекса от който сме тръгнали да пише в новия масив
        this.tail = this.head + this.size - 1; //новия теил е хеда + броя на елементите

        return temp;
    }

}
