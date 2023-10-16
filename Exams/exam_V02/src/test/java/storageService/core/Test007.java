package storageService.core;

import org.junit.Before;
import org.junit.Test;
import storageService.core.*;
import storageService.models.*;

public class Test007 {
    private StorageService storageService;

    private StorageUnit unit = new StorageUnit("first", 100, 0);
    private Box box = new Box("some", 10, 2, 2);

    @Before
    public void setup() {
        storageService = new StorageServiceImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void storeBoxLargerThanAvailableSpace() {
        storageService.rentStorage(unit);

        storageService.storeBox(box);
        storageService.storeBox(new Box("second", 10, 3, 3));
    }
}
