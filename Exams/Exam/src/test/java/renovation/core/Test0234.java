package renovation.core;

import renovation.core.*;
import renovation.models.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Test0234 {

    private Renovation renovationService;

    @Before
    public void setup() {
        this.renovationService = new RenovationImpl();
    }

    @Test
    public void testDeliverTile() {
        verifyCorrectness();
        renovationService = new RenovationImpl();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            double value = i * 0.000001;
            this.renovationService.deliverTile(new Tile(value, value, value));
        }
        long stop = System.currentTimeMillis();

        assertTrue(stop - start < 10);
    }

    private void verifyCorrectness() {
        Tile t1 = new Tile(2, 2, 0.5);

        this.renovationService.deliverTile(t1);

        assertEquals(4, this.renovationService.getDeliveredTileArea(), 0.0);
    }
}
