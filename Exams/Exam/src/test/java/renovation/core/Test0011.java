package renovation.core;

import renovation.models.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Test0011 {

    private Renovation renovationService;

    private final Tile t1 = new Tile(2, 2, 0.5);

    @Before
    public void setup() {
        this.renovationService = new RenovationImpl();
    }

    @Test
    public void testDeliverTile() {
        this.renovationService.deliverTile(t1);

        assertEquals(4, this.renovationService.getDeliveredTileArea(), 0.0);
    }
}