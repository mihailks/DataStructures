package implementations;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    public void testArrayDequeAdd() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(13);
        deque.add(14);
        deque.add(15);
        deque.add(16);
        deque.add(17);
    }

    @Test
    public void testArrayDequeAddFirst() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(13);
        deque.addFirst(14);
        deque.addFirst(15);
        deque.addFirst(16);
        deque.addFirst(17);
    }

    @Test
    public void TestGetByObject() { //test get by object
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(13);

        System.out.println(deque.get(Integer.valueOf(135)));

    }

    @Test
    public void TestRemoveByObject() { //test remove by object // FIXME: него не маха последният елемент, като хората
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("misho1");
        deque.add("misho2");
        deque.add("misho3");
        deque.add("misho4");
        deque.add("misho5");

        for (String s : deque) {
            System.out.println(s);
        }

       deque.remove("misho2");
        deque.add("misho6");
        deque.remove("misho1");
        deque.remove("misho4");
        deque.remove("misho5");
        deque.remove("misho6");
        System.out.println(deque.remove("misho3"));
    }

    @Test
    public void TestGet() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("misho1");
        deque.add("misho2");
        System.out.println(deque.get("misho2"));
    }

    @Test
    public void TestSet() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("misho1");
        deque.add("misho2");
        deque.set(1,"misho4");
    }

    @Test
    public void TestInsert() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("misho1");
        deque.add("misho2");
        deque.add("misho3");
        deque.add("misho4");
        deque.add("misho5");
        deque.add("misho6");
        deque.insert(1,"misho44");
        deque.insert(0,"misho43");
        deque.insert(7,"misho45");

        System.out.println();

        assertEquals("misho43", deque.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testInsertWrongIndexShouldThrow(){
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.insert(20,"test");
    }

        @Test
    public void TestRemoveByIndex() { //test remove by index // FIXME: него не маха последният елемент, като хората
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(13);
        deque.add(14);
        deque.add(15);
        deque.add(16);
        deque.add(17);

        System.out.println(deque.remove(1));
        System.out.println(deque.remove(1));
        System.out.println(deque.remove(1));
        deque.trimToSize();

    }

    @Test
    public void TestRemoveFirst(){
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.add(10);
        deque.add(6);
        deque.add(7);
        deque.add(8);
        deque.removeFirst();
        deque.removeLast();
        deque.removeFirst();
        deque.removeLast();
        deque.add(7);
        System.out.println();
    }

        @Test
    public void TestRemoveLast(){
        ArrayDeque<Integer> deque = new ArrayDeque<>();
            deque.add(10);
            deque.add(6);
            deque.add(7);
            deque.add(8);
            deque.removeLast();
            deque.removeLast();
            deque.removeLast();
            deque.removeLast();
            deque.add(7);
            System.out.println();
    }

    @Test
    public void testAddAndSize() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        assertTrue(deque.isEmpty());
        assertEquals(0, deque.size());

        deque.add(1);
        deque.add(2);

        assertFalse(deque.isEmpty());
        assertEquals(2, deque.size());
    }

    @Test
    public void testRemoveFirst() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.add("First");
        deque.add("Second");

        assertEquals("First", deque.removeFirst());
        assertEquals(1, deque.size());
        assertFalse(deque.isEmpty());
    }

    @Test
    public void testRemoveLast() {
        ArrayDeque<Double> deque = new ArrayDeque<>();
        deque.add(1.0);
        deque.add(2.0);

        assertEquals(Optional.of(2.0).orElse(null), deque.removeLast());
        assertEquals(1, deque.size());
        assertFalse(deque.isEmpty());
    }


}