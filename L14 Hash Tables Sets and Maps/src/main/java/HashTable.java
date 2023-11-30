import java.security.Key;
import java.util.*;

public class HashTable<K, V> implements Iterable<KeyValue<K, V>> {
    private LinkedList<KeyValue<K, V>>[] slots;
    private int count;
    private int capacity;

    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.80;

    public HashTable() {
        this(INITIAL_CAPACITY);
    }

    public HashTable(int capacity) {
        this.slots = new LinkedList[capacity];
        this.count = 0;
        this.capacity = capacity;
    }

    public void add(K key, V value) {
        this.growIfNeeded();

        int index = this.findSlotNumber(key);

        LinkedList<KeyValue<K, V>> list = this.slots[index];

        if (list == null) {
            list = new LinkedList<>();
        }
        for (KeyValue<K, V> current : list) {
            if (current.getKey().equals(key)) {
                throw new IllegalArgumentException("Key already exists: " + key);
            }
        }

        KeyValue<K, V> toInsert = new KeyValue<>(key, value);
        list.addLast(toInsert); // add the new element
        this.slots[index] = list; // add the chain to the list
        this.count++;
    }

    private int findSlotNumber(K key) {
        return Math.abs(key.hashCode()) % this.capacity;
    }

    private void growIfNeeded() {
        if (((double) (this.count + 1) / this.capacity) > LOAD_FACTOR) { // we add 1 to the count because we are about to add a new element, and we have to check if we need to grow
            this.grow();
        }
    }

    private void grow() {

        HashTable<K, V> newTable = new HashTable<>(this.capacity * 2);

        for (LinkedList<KeyValue<K, V>> slot : this.slots) {
            if (slot != null) {
                for (KeyValue<K, V> pair : slot) {
                    newTable.add(pair.getKey(), pair.getValue());
                }
            }
        }

        this.slots = newTable.slots;
        this.capacity *= 2;
    }

    public int size() {
        return this.count;
    }

    public int capacity() {
        return this.capacity;
    }

    public boolean addOrReplace(K key, V value) {
        this.growIfNeeded();

        int index = this.findSlotNumber(key);

        LinkedList<KeyValue<K, V>> list = this.slots[index];

        if (list == null) {
            list = new LinkedList<>();
        }
        boolean updated = false;
        for (KeyValue<K, V> current : list) {
            if (current.getKey().equals(key)) {
                current.setValue(value);
                updated = true;
            }
        }
        if (!updated) {
            KeyValue<K, V> toInsert = new KeyValue<>(key, value);
            list.addLast(toInsert); // add the new element
            this.count++;
        }

        this.slots[index] = list; // add the chain to the list
        return updated;
    }

    public V get(K key) {
        KeyValue<K, V> pair = this.find(key);
        if (pair == null) {
            throw new IllegalArgumentException();
        }
        return pair.getValue();
    }

    public KeyValue<K, V> find(K key) {
        int index = this.findSlotNumber(key);
        LinkedList<KeyValue<K, V>> slot = this.slots[index];
        if (slot == null) {
            return null;
        }
        for (KeyValue<K, V> pair : slot) {
            if (key.equals(pair.getKey())) {
                return pair;
            }
        }
        return null;
    }

    public boolean containsKey(K key) {
        return this.find(key) != null;
    }

    public boolean remove(K key) {
        int slotNumber = this.findSlotNumber(key);

        LinkedList<KeyValue<K, V>> slot = this.slots[slotNumber];

        if (slot == null) {
            return false;
        }

        for (KeyValue<K, V> pair : slot) {
            if (key.equals(pair.getKey())) {
                slot.remove(pair);
                this.count--;
                return true;
            }
        }
        return false;
    }


    public void clear() {
        this.capacity = INITIAL_CAPACITY;
        this.slots = new LinkedList[this.capacity];
        this.count = 0;
    }

    public Iterable<K> keys() {
        List<K> keys = new ArrayList<>();
        for (KeyValue<K, V> pair : this) {
            keys.add(pair.getKey());
        }
        return keys;
    }

    public Iterable<V> values() {
        List<V> values = new ArrayList<>();
        for (KeyValue<K, V> pair : this) {
            values.add(pair.getValue());
        }
        return values;
    }

    @Override
    public Iterator<KeyValue<K, V>> iterator() {
        return new Iterator<>() {
            Deque<KeyValue<K, V>> elements = new ArrayDeque<>();

            {
                for (LinkedList<KeyValue<K, V>> slot : slots) {
                    if (slot != null) {
                        for (KeyValue<K, V> pair : slot) {
                            elements.offer(pair);
                        }
                    }
                }
            }


            @Override
            public boolean hasNext() {
                return !elements.isEmpty();
            }

            @Override
            public KeyValue<K, V> next() {
                if (!hasNext()){
                    throw new NoSuchElementException();
                }

                return elements.poll();
            }
        };
    }
}
