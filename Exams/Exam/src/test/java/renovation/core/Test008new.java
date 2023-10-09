package renovation.core;

import renovation.models.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Test008new {

    private Renovation renovationService;

    private final Tile t1 = new Tile(3, 3, 0.5);

    @Before
    public void setup() {
        this.renovationService = new RenovationImpl();
    }

    @Test
    public void testReturnTile() {
        this.renovationService.deliverTile(t1);

        this.renovationService.returnTile(t1);

        assertEquals(0, this.renovationService.getDeliveredTileArea(), 0.0);
    }
}
