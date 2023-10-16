package storageService.core;

import org.junit.Before;
import org.junit.Test;
import storageService.core.*;
import storageService.models.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Test014 {
    private StorageService storageService;

    private StorageUnit unit = new StorageUnit("first", 100, 0);
    private Box box = new Box("some", 10, 2, 2);

    @Before
    public void setup() {
        storageService = new StorageServiceImpl();
    }

    @Test
    public void contains() {
        List<StorageUnit> units = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            units.add(new StorageUnit("" + i, i * 10, 0));
            storageService.rentStorage(units.get(i));
        }

        storageService.storeBox(box);

        for (int i = 0; i < 99; i++) {
            assertFalse(storageService.contains(units.get(i), box.id));
        }

        assertTrue(storageService.contains(units.get(99), box.id));
    }
}