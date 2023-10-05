import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BinarySearchTreeTest {
    private BinarySearchTree<Integer> bst;

    @Before
    public void beforeEach() {
        bst = new BinarySearchTree<>(5);
        bst.insert(3);
        bst.insert(7);
        bst.insert(6);
        bst.insert(1);
        bst.insert(17);
    }

    @Test
    public void testCreate() {
        assertEquals(Integer.valueOf(5), bst.getRoot().getValue());
    }

    @Test
    public void testInsert() {
        assertEquals(Integer.valueOf(3), bst.getRoot().getLeft().getValue());
        assertEquals(Integer.valueOf(7), bst.getRoot().getRight().getValue());
        assertEquals(Integer.valueOf(6), bst.getRoot().getRight().getLeft().getValue());
    }

    @Test
    public void testEachInOrder() {

        List<Integer> elements = new ArrayList<>();
        bst.eachInOrder(elements::add);

        List<Integer> expected = new ArrayList<>(Arrays.asList(1, 3, 5, 6, 7, 17));

        assertEquals(Integer.valueOf(3), bst.getRoot().getLeft().getValue());
        assertEquals(Integer.valueOf(7), bst.getRoot().getRight().getValue());
        assertEquals(Integer.valueOf(6), bst.getRoot().getRight().getLeft().getValue());

        assertEquals(expected.size(), elements.size());
        for (int i = 0; i < elements.size(); i++) {
            assertEquals(expected.get(i), elements.get(i));
        }
    }

    @Test
    public void testContainsTrue() {
        assertTrue(bst.contains(17));
    }

    @Test
    public void testContainsFalse() {
        assertFalse(bst.contains(42));
    }

    @Test
    public void testSearchTrue() {
        //трябва да връща копие на дървото
        BinarySearchTree<Integer> search = bst.search(7);
        //за да прверим вкараме нова стойност в едното, следователно ако са на истина копирани другото няма да я има
        bst.insert(8);

        assertEquals(Integer.valueOf(7), search.getRoot().getValue());
        assertEquals(Integer.valueOf(6), search.getRoot().getLeft().getValue());
        assertEquals(Integer.valueOf(17), search.getRoot().getRight().getValue());


        assertTrue(bst.contains(8));
        assertFalse(search.contains(8));
    }

    @Test
    public void testSearchFalse() {
        assertEquals(null, bst.search(42));
    }

    @Test
    public void testRange() {
        List<Integer> range = bst.range(3, 7);
        // 3,5,6,7
        List<Integer> expected = Arrays.asList(3, 5, 7, 6);

        assertEquals(4, range.size());
        for (Integer value : expected) {
            assertTrue(expected.contains(value));
        }

    }

    @Test
    public void testDeleteMin() {
        assertTrue(bst.contains(1));
        bst.deleteMin();
        assertFalse(bst.contains(1));
    }

    @Test
    public void testDeleteMax() {
        assertTrue(bst.contains(17));
        bst.deleteMax();
        assertFalse(bst.contains(17));
    }

    @Test
    public void testCount() {
        assertEquals(6, bst.count());
    }

    @Test
    public void testCountAfterInsert() {
        bst.insert(42);
        assertEquals(7, bst.count());
    }

    @Test
    public void testCountAfterDeleteMin() {
        bst.deleteMin();
        assertEquals(5, bst.count());
    }

    @Test
    public void testCountAfterDeleteMax() {
        bst.deleteMax();
        assertEquals(5, bst.count());
    }

    @Test
    public void testRank() {
        assertEquals(4, bst.rank(7));
    }

    @Test
    public void testRankMinElement() {
        assertEquals(0, bst.rank(-1));
    }

    @Test
    public void testRankEmptyTree() {
        assertEquals(0, new BinarySearchTree<Integer>().rank(1));
    }

    @Test
    public void testFloor() {
        assertEquals(Integer.valueOf(6), bst.floor(7));
    }

    @Test
    public void testFloorEmpty() {
        assertNull(bst.floor(-1));
    }

    @Test
    public void testCeil() {
        assertEquals(Integer.valueOf(7), bst.ceil(6));
    }

    @Test
    public void testCeilEmpty() {
        assertNull(bst.ceil(20));
    }
}









