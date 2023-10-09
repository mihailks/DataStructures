package renovation.core;

import renovation.core.*;
import renovation.models.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Test02634 {

    private Renovation renovationService;

    private final Laminate l1 = new Laminate(-1, -1, WoodType.OAK);

    @Before
    public void setup() {
        this.renovationService = new RenovationImpl();
    }

    @Test
    public void testLaminateIsDelivered() {
        verifyCorrectness();
        renovationService = new RenovationImpl();

        this.renovationService.deliverFlooring(l1);
        for (int i = 0; i < 10000; i++) {
            this.renovationService.deliverFlooring(new Laminate(i, i, WoodType.OAK));
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            this.renovationService.isDelivered(l1);
        }
        long stop = System.currentTimeMillis();

        assertTrue(stop - start < 120);
    }

    private void verifyCorrectness() {
        Laminate l1 = new Laminate(2.2, 0.3, WoodType.OAK);

        assertFalse(this.renovationService.isDelivered(l1));

        this.renovationService.deliverFlooring(l1);

        assertTrue(this.renovationService.isDelivered(l1));
    }
}

