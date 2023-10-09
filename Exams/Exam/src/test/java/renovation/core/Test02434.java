package renovation.core;

import renovation.core.*;
import renovation.models.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Test02434 {

    private Renovation renovationService;

    @Before
    public void setup() {
        this.renovationService = new RenovationImpl();
    }

    @Test
    public void testDeliverFlooring() {
        verifyCorrectness();
        this.renovationService = new RenovationImpl();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            this.renovationService.deliverFlooring(new Laminate(i, i, WoodType.OAK));
        }
        long stop = System.currentTimeMillis();

        assertTrue(stop - start < 20);
    }

    private void verifyCorrectness() {
        Laminate l1 = new Laminate(2.2, 0.3, WoodType.OAK);

        assertFalse(this.renovationService.isDelivered(l1));

        this.renovationService.deliverFlooring(l1);

        assertTrue(this.renovationService.isDelivered(l1));
    }
}
