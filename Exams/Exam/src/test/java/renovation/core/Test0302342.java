package renovation.core;

import renovation.core.*;
import renovation.models.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class Test0302342 {

    private Renovation renovationService;

    @Before
    public void setup() {
        this.renovationService = new RenovationImpl();
    }

    @Test
    public void testReturnLaminate() {
        List<Laminate> laminates = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            laminates.add(new Laminate(i, i, WoodType.OAK));

            this.renovationService.deliverFlooring(laminates.get(i));
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Iterator<Laminate> laminateIterator = this.renovationService.layFlooring();
            while (laminateIterator.hasNext()) { laminateIterator.next(); }
        }
        long stop = System.currentTimeMillis();

        assertTrue(stop - start < 420);
    }

    private void verifyCorrectness() {
        Laminate l1 = new Laminate(2.1, 0.4, WoodType.OAK);
        Laminate l2 = new Laminate(2.2, 0.4, WoodType.OAK);
        Laminate l3 = new Laminate(2.3, 0.4, WoodType.OAK);
        Laminate l4 = new Laminate(2.4, 0.4, WoodType.OAK);

        List<Laminate> expected = List.of(l4, l3, l2, l1);
        Iterator<Laminate> expectedIterator = expected.iterator();

        this.renovationService.deliverFlooring(l1);
        this.renovationService.deliverFlooring(l2);
        this.renovationService.deliverFlooring(l3);
        this.renovationService.deliverFlooring(l4);

        Iterator<Laminate> laminateIterator = this.renovationService.layFlooring();

        while (laminateIterator.hasNext()) {
            assertEquals(expectedIterator.next(), laminateIterator.next());
        }

        assertFalse(expectedIterator.hasNext());
    }

}