package renovation.core;

import renovation.core.*;
import renovation.models.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Test02734 {

    private Renovation renovationService;

    @Before
    public void setup() {
        this.renovationService = new RenovationImpl();
    }

    @Test
    public void testReturnTile() {
        verifyCorrectness();
        renovationService = new RenovationImpl();

        List<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            double value = i * 0.000001;
            tiles.add(new Tile(value, value, value));

            this.renovationService.deliverTile(tiles.get(i));
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            this.renovationService.returnTile(tiles.get(i));
        }
        long stop = System.currentTimeMillis();

        assertTrue(stop - start < 150);
    }

    private void verifyCorrectness() {
        Tile t1 = new Tile(2, 2, 0.5);

        this.renovationService.deliverTile(t1);

        assertEquals(4, this.renovationService.getDeliveredTileArea(), 0.0);

        this.renovationService.returnTile(t1);

        assertEquals(0, this.renovationService.getDeliveredTileArea(), 0.0);
    }
}