package storageService.core;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import storageService.models.Box;
import storageService.models.StorageUnit;

import java.util.Collection;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class StorageServiceTests {
    private StorageService storageService;

    private final StorageUnit unit = new StorageUnit("first", 100, 0);
    private final Box box = new Box("some", 10, 2, 2);

    @Before
    public void setup() {
        storageService = new StorageServiceImpl();
    }

    @Test
    public void rentStorage() {
        storageService.rentStorage(unit);

        assertEquals(100, storageService.getTotalFreeSpace());
    }

    @Test
    public void storeBox() {
        storageService.rentStorage(unit);

        storageService.storeBox(box);

        TestCase.assertEquals(60, storageService.getTotalFreeSpace());
    }

    @Test(expected = IllegalArgumentException.class)
    public void storeSameBox() {
        storageService.rentStorage(unit);

        storageService.storeBox(box);
        storageService.storeBox(box);
    }

    @Test
    public void isRented() {
        storageService.rentStorage(unit);

        assertTrue(storageService.isRented(unit));
    }

    @Test
    public void containsWithNonExistingUnit() {
        assertFalse(storageService.contains(unit, box.id));
    }

    @Test
    public void retrievesWithStoredBox() {
        storageService.rentStorage(unit);
        storageService.storeBox(box);

        assertEquals(storageService.retrieve(unit, box.id), box);
        assertEquals(100, storageService.getTotalFreeSpace());
    }

    @Test
    public void getAllBoxesOnEmptyService() {
        Collection<Box> allBoxesByVolume = storageService.getAllBoxesByVolume();

        assertTrue(allBoxesByVolume.isEmpty());
    }

    @Test
    public void testGetTotalFreeSpace() {

        StorageUnit unit0 = new StorageUnit("0", 100, 0);
        StorageUnit unit1 = new StorageUnit("1", 100, 0);
        StorageUnit unit2 = new StorageUnit("2", 100, 0);
        StorageUnit unit3 = new StorageUnit("3", 100, 0);
        Box box0 = new Box("0", 10, 2, 2);
        Box box1 = new Box("1", 10, 2, 2);
        Box box2 = new Box("2", 10, 2, 2);
        Box box3 = new Box("3", 10, 2, 2);

        storageService.rentStorage(unit0);
        storageService.rentStorage(unit1);
        storageService.rentStorage(unit2);
        storageService.rentStorage(unit3);

        storageService.storeBox(box0);
        storageService.storeBox(box1);
        storageService.storeBox(box2);
        storageService.storeBox(box3);

        TestCase.assertEquals(240, storageService.getTotalFreeSpace());

        storageService.retrieve(unit0, box0.getId());

        TestCase.assertEquals(280, storageService.getTotalFreeSpace());


    }

    @Test
    public void testAllBoxesByVolume() {

        StorageUnit unit0 = new StorageUnit("0", 10000, 0);
        StorageUnit unit1 = new StorageUnit("1", 10000, 0);
        StorageUnit unit2 = new StorageUnit("2", 10000, 0);
        StorageUnit unit3 = new StorageUnit("3", 10000, 0);
        Box box0 = new Box("0", 10, 1, 2);
        Box box1 = new Box("1", 20, 2, 2);
        Box box2 = new Box("2", 30, 3, 2);
        Box box3 = new Box("3", 40, 4, 2);

        storageService.rentStorage(unit0);
        storageService.rentStorage(unit1);
        storageService.rentStorage(unit2);
        storageService.rentStorage(unit3);

        storageService.storeBox(box0);
        storageService.storeBox(box1);
        storageService.storeBox(box2);
        storageService.storeBox(box3);

        Collection<Box> allBoxesByVolume = storageService.getAllBoxesByVolume();

    }

    @Test
    public void testAllUnitsByFillRate() {

        StorageUnit unit0 = new StorageUnit("0", 100, 0);
        StorageUnit unit1 = new StorageUnit("1", 100, 0);
        StorageUnit unit2 = new StorageUnit("2", 100, 0);
        StorageUnit unit3 = new StorageUnit("3", 100, 0);
        Box box0 = new Box("0", 10, 4, 2);
        Box box1 = new Box("1", 1, 1, 2);
        Box box2 = new Box("2", 10, 4, 2);
        Box box3 = new Box("3", 10, 1, 2);

        storageService.rentStorage(unit0);
        storageService.rentStorage(unit1);
        storageService.rentStorage(unit2);
        storageService.rentStorage(unit3);

        storageService.storeBox(box0);
        storageService.storeBox(box1);
        storageService.storeBox(box2);
        storageService.storeBox(box3);

        Collection<StorageUnit> allUnitsByFillRate = storageService.getAllUnitsByFillRate();

    }
}













