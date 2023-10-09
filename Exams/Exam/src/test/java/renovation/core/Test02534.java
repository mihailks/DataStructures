package renovation.core;

import renovation.core.*;
import renovation.models.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Test02534 {

    private Renovation renovationService;

    @Before
    public void setup() {
        this.renovationService = new RenovationImpl();
    }

    @Test
    public void testTotalTileArea() {
        verifyCorrectness();
        renovationService = new RenovationImpl();

        for (int i = 0; i < 10000; i++) {
            double value = i * 0.000001;
            this.renovationService.deliverTile(new Tile(value, value, value));
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            this.renovationService.getDeliveredTileArea();
        }
        long stop = System.currentTimeMillis();

        assertTrue(stop - start < 10);
    }

    private void verifyCorrectness() {
        Tile t1 = new Tile(0.5, 0.5, 0.5);

        for (int i = 0; i < 79; i++) {
            this.renovationService.deliverTile(t1);
        }

        assertEquals(19.75, this.renovationService.getDeliveredTileArea(), 0.0);
    }
}
