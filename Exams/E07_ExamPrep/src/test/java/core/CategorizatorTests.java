package core;

import models.Category;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

public class CategorizatorTests {
    private interface InternalTest {
        void execute();
    }

    private Categorizator categorizator;

    private Category getRandomCategory() {
        return new Category(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString());
    }

    @Before
    public void setup() {
        this.categorizator = new CategorizatorImpl();
    }

    public void performCorrectnessTesting(InternalTest[] methods) {
        Arrays.stream(methods)
                .forEach(method -> {
                    this.categorizator = new CategorizatorImpl();

                    try {
                        method.execute();
                    } catch (IllegalArgumentException ignored) { }
                });

        this.categorizator = new CategorizatorImpl();
    }

    // Correctness Tests

    @Test
    public void testSize_ShouldReturnCorrectResults() {
        this.categorizator.addCategory(getRandomCategory());
        this.categorizator.addCategory(getRandomCategory());
        this.categorizator.addCategory(getRandomCategory());

        assertEquals(this.categorizator.size(), 3);
    }

    @Test
    public void testAddCategory_WithDuplicate_ShouldThrow() {
        Category category = this.getRandomCategory();
        this.categorizator.addCategory(category);

        // Little bit of hacks
        try {
            this.categorizator.addCategory(category);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }

        assertTrue(false);
    }

    @Test
    public void testRemoveCategory() {
        categorizator.addCategory(new Category("A", "Alpha", ""));
        categorizator.addCategory(new Category("B", "Bravo", ""));
        categorizator.addCategory(new Category("C", "Charlie", ""));
        categorizator.addCategory(new Category("D", "Delta", ""));
        categorizator.addCategory(new Category("E", "Echo", ""));
        categorizator.addCategory(new Category("F", "Foxtrot", ""));
        categorizator.addCategory(new Category("G", "Golf", ""));
        categorizator.addCategory(new Category("H", "Husky", ""));

        categorizator.assignParent("B", "A");
            categorizator.assignParent("D", "B");
            categorizator.assignParent("E", "B");
        categorizator.assignParent("C", "A");
            categorizator.assignParent("F", "C");
                categorizator.assignParent("G", "F");
                categorizator.assignParent("H", "F");

        assertEquals(8, categorizator.size());

        categorizator.removeCategory("C");

        assertEquals(4, categorizator.size());
    }

    @Test
    public void testRemoveNeighbourCategories() {
        categorizator.addCategory(new Category("A", "Alpha", ""));
        categorizator.addCategory(new Category("B", "Bravo", ""));
        categorizator.addCategory(new Category("C", "Charlie", ""));
        categorizator.addCategory(new Category("D", "Delta", ""));
        categorizator.addCategory(new Category("E", "Echo", ""));
        categorizator.addCategory(new Category("F", "Foxtrot", ""));
        categorizator.addCategory(new Category("G", "Golf", ""));
        categorizator.addCategory(new Category("H", "Husky", ""));

        categorizator.assignParent("B", "A");
        categorizator.assignParent("D", "B");
        categorizator.assignParent("E", "B");
        categorizator.assignParent("C", "A");
        categorizator.assignParent("F", "C");
        categorizator.assignParent("G", "F");
        categorizator.assignParent("H", "F");

        assertEquals(8, categorizator.size());

        categorizator.removeCategory("C");
        categorizator.removeCategory("B");

        assertEquals(1, categorizator.size());
    }

    @Test
    public void testRemoveCategoryTwice() {
        categorizator.addCategory(new Category("A", "Alpha", ""));
        categorizator.addCategory(new Category("B", "Bravo", ""));
        categorizator.addCategory(new Category("C", "Charlie", ""));
        categorizator.addCategory(new Category("D", "Delta", ""));
        categorizator.addCategory(new Category("E", "Echo", ""));
        categorizator.addCategory(new Category("F", "Foxtrot", ""));
        categorizator.addCategory(new Category("G", "Golf", ""));
        categorizator.addCategory(new Category("H", "Husky", ""));

        categorizator.assignParent("B", "A");
            categorizator.assignParent("D", "B");
            categorizator.assignParent("E", "B");
        categorizator.assignParent("C", "A");
            categorizator.assignParent("F", "C");
                categorizator.assignParent("G", "F");
                categorizator.assignParent("H", "F");

        assertEquals(8, categorizator.size());

        categorizator.removeCategory("C");

        assertEquals(4, categorizator.size());

        IllegalArgumentException expected = null;
        try {
            categorizator.removeCategory("C");
        } catch (IllegalArgumentException e) {
            expected = e;
        }

        if (expected == null) {
            fail();
        }
    }

    @Test
    public void testRemoveSingleCategory() {
        categorizator.addCategory(new Category("A", "Alpha", ""));
        categorizator.removeCategory("A");

        assertEquals(0, categorizator.size());
    }

    @Test
    public void testRemoveLeafCategory() {
        categorizator.addCategory(new Category("A", "Alpha", ""));
        categorizator.addCategory(new Category("B", "Bravo", ""));

        categorizator.assignParent("B", "A");

        categorizator.removeCategory("B");

        assertEquals(1, categorizator.size());
    }

    @Test
    public void testRemoveRootCategory() {
        categorizator.addCategory(new Category("A", "Alpha", ""));
        categorizator.addCategory(new Category("B", "Bravo", ""));
        categorizator.addCategory(new Category("C", "Charlie", ""));
        categorizator.addCategory(new Category("D", "Delta", ""));
        categorizator.addCategory(new Category("E", "Echo", ""));
        categorizator.addCategory(new Category("F", "Foxtrot", ""));
        categorizator.addCategory(new Category("G", "Golf", ""));
        categorizator.addCategory(new Category("H", "Husky", ""));

        categorizator.assignParent("B", "A");
        categorizator.assignParent("D", "B");
        categorizator.assignParent("E", "B");
        categorizator.assignParent("C", "A");
        categorizator.assignParent("F", "C");
        categorizator.assignParent("G", "F");
        categorizator.assignParent("H", "F");

        assertEquals(8, categorizator.size());

        categorizator.removeCategory("A");

        assertEquals(0, categorizator.size());
    }

    @Test
    public void testGetHierarchyNone() {
        categorizator.addCategory(new Category("A", "Alpha", ""));
        IllegalArgumentException expected = null;
        try {
            Iterator<Category> iterator = categorizator.getHierarchy("NONEXISTING").iterator();
        } catch (IllegalArgumentException e) {
            expected = e;
        }

        assertNotNull(expected);
    }

    @Test
    public void testGetHierarchyRoot() {
        categorizator.addCategory(new Category("A", "Alpha", ""));
        Iterator<Category> iterator = categorizator.getHierarchy("A").iterator();
        assertEquals("A", iterator.next().getId());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testGetHierarchy() {
        categorizator.addCategory(new Category("A", "Alpha", ""));
        categorizator.addCategory(new Category("B", "Bravo", ""));
        categorizator.addCategory(new Category("C", "Charlie", ""));
        categorizator.addCategory(new Category("D", "Delta", ""));
        categorizator.addCategory(new Category("E", "Echo", ""));
        categorizator.addCategory(new Category("F", "Foxtrot", ""));
        categorizator.addCategory(new Category("G", "Golf", ""));
        categorizator.addCategory(new Category("H", "Husky", ""));

        categorizator.assignParent("B", "A");
            categorizator.assignParent("D", "B");
            categorizator.assignParent("E", "B");
        categorizator.assignParent("C", "A");
            categorizator.assignParent("F", "C");
                categorizator.assignParent("G", "F");
                categorizator.assignParent("H", "F");

        Iterator<Category> iterator = categorizator.getHierarchy("F").iterator();
        assertEquals("A", iterator.next().getId());
        assertEquals("C", iterator.next().getId());
        assertEquals("F", iterator.next().getId());
    }

    @Test
    public void testGetTop3() {
        categorizator.addCategory(new Category("A", "Alpha", ""));
        categorizator.addCategory(new Category("B", "Bravo", ""));
        categorizator.addCategory(new Category("C", "Charlie", ""));
        categorizator.addCategory(new Category("D", "Delta", ""));
        categorizator.addCategory(new Category("E", "Echo", ""));
        categorizator.addCategory(new Category("F", "Foxtrot", ""));
        categorizator.addCategory(new Category("G", "Golf", ""));
        categorizator.addCategory(new Category("H", "Husky", ""));

        categorizator.assignParent("B", "A");
            categorizator.assignParent("D", "B");
            categorizator.assignParent("E", "B");
        categorizator.assignParent("C", "A");
            categorizator.assignParent("F", "C");
                categorizator.assignParent("G", "F");
                categorizator.assignParent("H", "F");

        assertEquals(8, categorizator.size());

        Iterable<Category> top3CategoriesOrderedByDepthOfChildrenThenByName = categorizator.getTop3CategoriesOrderedByDepthOfChildrenThenByName();
        Iterator<Category> iterator = top3CategoriesOrderedByDepthOfChildrenThenByName.iterator();
        Category top = iterator.next();
        Category second = iterator.next();
        Category third = iterator.next();

        assertEquals("A", top.getId());
        assertEquals("C", second.getId());
        assertEquals("B", third.getId());
    }

    @Test
    public void testGetTop3WithSingleElement() {
        categorizator.addCategory(new Category("A", "Alpha", ""));

        Iterator<Category> iterator = categorizator.getTop3CategoriesOrderedByDepthOfChildrenThenByName().iterator();
        assertEquals("A", iterator.next().getId());
    }

    @Test
    public void testGetTop3WithTwoTrees() {
        categorizator.addCategory(new Category("A", "Alpha", ""));
        categorizator.addCategory(new Category("B", "Bravo", ""));
        categorizator.addCategory(new Category("C", "Charlie", ""));
        categorizator.addCategory(new Category("D", "Delta", ""));
        categorizator.addCategory(new Category("E", "Echo", ""));
        categorizator.addCategory(new Category("F", "Foxtrot", ""));
        categorizator.addCategory(new Category("G", "Golf", ""));
        categorizator.addCategory(new Category("H", "Husky", ""));

        categorizator.addCategory(new Category("Q", "Quebec", ""));
        categorizator.addCategory(new Category("M", "Mike", ""));
        categorizator.addCategory(new Category("N", "November", ""));
        categorizator.addCategory(new Category("O", "Oscar", ""));
        categorizator.addCategory(new Category("P", "Papa", ""));

        categorizator.assignParent("B", "A");
        categorizator.assignParent("D", "B");
        categorizator.assignParent("E", "B");
        categorizator.assignParent("C", "A");
        categorizator.assignParent("F", "C");
        categorizator.assignParent("G", "F");
        categorizator.assignParent("H", "F");

        categorizator.assignParent("M", "Q");
        categorizator.assignParent("N", "Q");
        categorizator.assignParent("O", "N");
        categorizator.assignParent("P", "O");

        Iterable<Category> top3CategoriesOrderedByDepthOfChildrenThenByName = categorizator.getTop3CategoriesOrderedByDepthOfChildrenThenByName();
        Iterator<Category> iterator = top3CategoriesOrderedByDepthOfChildrenThenByName.iterator();
        Category top = iterator.next();
        Category second = iterator.next();
        Category third = iterator.next();

        assertEquals("A", top.getId());
        assertEquals("Q", second.getId());
        assertEquals("C", third.getId());
    }

    @Test
    public void testGetChildrenRoot() {
        categorizator.addCategory(new Category("A", "Alpha", ""));
        categorizator.addCategory(new Category("B", "Bravo", ""));
        categorizator.addCategory(new Category("C", "Charlie", ""));
        categorizator.addCategory(new Category("D", "Delta", ""));
        categorizator.addCategory(new Category("E", "Echo", ""));
        categorizator.addCategory(new Category("F", "Foxtrot", ""));
        categorizator.addCategory(new Category("G", "Golf", ""));
        categorizator.addCategory(new Category("H", "Husky", ""));

        categorizator.assignParent("B", "A");
            categorizator.assignParent("D", "B");
            categorizator.assignParent("E", "B");
        categorizator.assignParent("C", "A");
            categorizator.assignParent("F", "C");
                categorizator.assignParent("G", "F");
                categorizator.assignParent("H", "F");

        Iterator<Category> iterator = categorizator.getChildren("A").iterator();
        assertEquals("B", iterator.next().getId());
        assertEquals("D", iterator.next().getId());
        assertEquals("E", iterator.next().getId());
        assertEquals("C", iterator.next().getId());
        assertEquals("F", iterator.next().getId());
        assertEquals("G", iterator.next().getId());
        assertEquals("H", iterator.next().getId());
    }

    @Test
    public void testGetChildren() {
        categorizator.addCategory(new Category("A", "Alpha", ""));
        categorizator.addCategory(new Category("B", "Bravo", ""));
        categorizator.addCategory(new Category("C", "Charlie", ""));
        categorizator.addCategory(new Category("D", "Delta", ""));
        categorizator.addCategory(new Category("E", "Echo", ""));
        categorizator.addCategory(new Category("F", "Foxtrot", ""));
        categorizator.addCategory(new Category("G", "Golf", ""));
        categorizator.addCategory(new Category("H", "Husky", ""));

        categorizator.assignParent("B", "A");
        categorizator.assignParent("D", "B");
        categorizator.assignParent("E", "B");
        categorizator.assignParent("C", "A");
        categorizator.assignParent("F", "C");
        categorizator.assignParent("G", "F");
        categorizator.assignParent("H", "F");

        Iterator<Category> iterator = categorizator.getChildren("C").iterator();
        assertEquals("F", iterator.next().getId());
        assertEquals("G", iterator.next().getId());
        assertEquals("H", iterator.next().getId());
    }

    @Test
    public void testGetChildrenLeaf() {
        categorizator.addCategory(new Category("A", "Alpha", ""));
        categorizator.addCategory(new Category("B", "Bravo", ""));
        categorizator.addCategory(new Category("C", "Charlie", ""));
        categorizator.addCategory(new Category("D", "Delta", ""));
        categorizator.addCategory(new Category("E", "Echo", ""));
        categorizator.addCategory(new Category("F", "Foxtrot", ""));
        categorizator.addCategory(new Category("G", "Golf", ""));
        categorizator.addCategory(new Category("H", "Husky", ""));

        categorizator.assignParent("B", "A");
        categorizator.assignParent("D", "B");
        categorizator.assignParent("E", "B");
        categorizator.assignParent("C", "A");
        categorizator.assignParent("F", "C");
        categorizator.assignParent("G", "F");
        categorizator.assignParent("H", "F");

        Iterator<Category> iterator = categorizator.getChildren("G").iterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testContains_WithExistentCategory_ShouldReturnTrue() {
        Category category = getRandomCategory();
        this.categorizator.addCategory(category);

        assertTrue(this.categorizator.contains(category));
    }

    @Test
    public void testContains_WithNonExistentCategory_ShouldReturnFalse() {
        Category category = getRandomCategory();
        this.categorizator.addCategory(category);

        assertFalse(this.categorizator.contains(getRandomCategory()));
    }

    @Test
    public void testAssignParent_WithDuplicateCategories_ShouldReturnCorrectResults() {
        Category childCategory = getRandomCategory();
        Category parentCategory = getRandomCategory();
        this.categorizator.addCategory(childCategory);
        this.categorizator.addCategory(parentCategory);

        this.categorizator.assignParent(childCategory.getId(), parentCategory.getId());

        // Little bit of hacks
        try {
            this.categorizator.assignParent(childCategory.getId(), parentCategory.getId());
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }

        assertTrue(false);
    }

    // Performance Tests

    @Test
    public void testContains_With100000Results_ShouldPassQuickly() {
        this.performCorrectnessTesting(new InternalTest[] {
                this::testContains_WithExistentCategory_ShouldReturnTrue,
                this::testContains_WithNonExistentCategory_ShouldReturnFalse,
        });

        int count = 100000;

        Category categoryToContain = null;

        for (int i = 0; i < count; i++)
        {
            if(i == count / 2) {
                categoryToContain = getRandomCategory();
                this.categorizator.addCategory(categoryToContain);
            } else {
                this.categorizator.addCategory(getRandomCategory());
            }
        }

        long start = System.currentTimeMillis();

        this.categorizator.contains(categoryToContain);

        long stop = System.currentTimeMillis();

        long elapsedTime = stop - start;

        assertTrue(elapsedTime <= 5);
    }
}
