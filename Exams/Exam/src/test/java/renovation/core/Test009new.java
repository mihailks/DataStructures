package renovation.core;

import renovation.models.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Test009new {

    private Renovation renovationService;


    private final Tile t1 = new Tile(2, 2, 0.5);
    private final Tile t2 = new Tile(3, 3, 0.5);
    private final Tile t3 = new Tile(4, 4, 0.5);
    private final Tile t4 = new Tile(0.3, 3, 0.5);


    @Before
    public void setup() {
        this.renovationService = new RenovationImpl();
    }

    @Test
    public void testReturnTile2() {
        this.renovationService.deliverTile(t1);
        this.renovationService.deliverTile(t2);
        this.renovationService.deliverTile(t3);
        this.renovationService.deliverTile(t4);

        this.renovationService.returnTile(t1);
        this.renovationService.returnTile(t4);

        assertEquals(25, this.renovationService.getDeliveredTileArea(), 0.0);
    }
}

