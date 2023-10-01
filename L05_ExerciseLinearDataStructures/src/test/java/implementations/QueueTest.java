package implementations;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class QueueTest {
    private Queue<String> queue;

    @Before
    public void setUp() {
        this.queue = new Queue<>();
        for (int i = 0; i < 100; i++) {
            queue.offer(String.valueOf(i));
        }
    }

    @Test
    public void testOfferShouldAddAtTheEnd() {
        queue.offer("Slayer");
        assertEquals("0", queue.peek());
    }

    @Test
    public void testPopShouldRemoveAndReturnTopElement() {
        assertEquals("0", queue.poll());
        assertEquals(99, queue.size());
    }

    @Test(expected = IllegalStateException.class)
    public void testPopShouldThrowWhenEmpty() {
        new Queue<>().poll();
    }

    @Test
    public void testPeekShouldReturnAndNotRemove() {
        assertEquals("0", queue.peek());
        assertEquals(100, queue.size());
    }

    @Test(expected = IllegalStateException.class)
    public void testPeekShouldThrowWhenEmpty() {
        new Queue<>().peek();
    }

    @Test
    public void testSize() {
        assertEquals(100, queue.size());
        assertEquals(0, new Queue<>().size());
    }


    @Test
    public void testIsEmpty() {
        assertTrue(new Queue<>().isEmpty());
        assertFalse(queue.isEmpty());
    }

    @Test
    public void testIteratorShouldTraversItemsFromBeginToEnd() {
        int last = 0;
        for (String s : queue) {
            assertEquals(String.valueOf(last++), s);
        }
    }

    @Test
    public void testChainingOfNewElements(){
        Queue<Integer> queue = new Queue<>();
        new Random()
                .ints(100)
                .forEach(queue::offer);
    }

    @Test
    public void testAddAndRemoveSingleElement(){
        Queue<Integer> queue = new Queue<>();
        queue.offer(10);
        queue.offer(10);
        Integer firstPoll = queue.poll();
        queue.poll();
        assertEquals((Integer) 10,firstPoll);
        assertEquals(0, queue.size());
    }

}