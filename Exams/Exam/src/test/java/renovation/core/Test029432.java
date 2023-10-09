package renovation.core;

import renovation.core.*;
import renovation.models.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class Test029432 {

    private Renovation renovationService;

    @Before
    public void setup() {
        this.renovationService = new RenovationImpl();
    }

    @Test
    public void testSortTilesByDepth() {
        verifyCorrectness();
        this.renovationService = new RenovationImpl();

        List<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            double value = i * 0.000001;
            tiles.add(new Tile(value, value, value % 100));

            this.renovationService.deliverTile(tiles.get(i));
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            this.renovationService.sortTilesBySize();
        }
        long stop = System.currentTimeMillis();

        assertTrue(stop - start < 4400);
    }

    private void verifyCorrectness() {
        Tile t1 = new Tile(1, 1, 0.5);
        Tile t2 = new Tile(2, 2, 0.5);
        Tile t3 = new Tile(3, 3, 0.7);
        Tile t4 = new Tile(4, 1, 0.8);

        this.renovationService.deliverTile(t1);
        this.renovationService.deliverTile(t2);
        this.renovationService.deliverTile(t3);
        this.renovationService.deliverTile(t4);

        Collection<Tile> tilesBySize = this.renovationService.sortTilesBySize();

        assertEquals(tilesBySize.size(), 4);

        List<Tile> expected = List.of(t1, t2, t4, t3);
        Iterator<Tile> actualIterator = tilesBySize.iterator();
        int i = 0;

        while(actualIterator.hasNext()) {
            assertEquals(expected.get(i++), actualIterator.next());
        }
    }
}

