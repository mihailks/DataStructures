package craftsmanLab.core;

import craftsmanLab.core.*;
import craftsmanLab.models.*;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class Test027 {

    private CraftsmanLab lab;

    @Before
    public void setup() {
        this.lab = new CraftsmanLabImpl();
    }

    @Test
    public void removeCraftsman() {
        verifyCorrectness();
        lab = new CraftsmanLabImpl();

        List<Craftsman> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            Craftsman craftsman = new Craftsman(String.valueOf(i), 10 * i, 10 * i);
            list.add(craftsman);
            this.lab.addCraftsman(craftsman);
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            this.lab.removeCraftsman(list.get(9999 - i));
        }
        long stop = System.currentTimeMillis();

        assertTrue(stop - start < 60);
    }

    private void verifyCorrectness() {
        List<Craftsman> craftsmen = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Craftsman craftsman = new Craftsman("Bai " + i, 100, 0);

            craftsmen.add(craftsman);
            lab.addCraftsman(craftsman);
        }

        assertTrue(lab.exists(craftsmen.get(50)));

        try {
            lab.addCraftsman(craftsmen.get(50));
            fail();
        } catch (IllegalArgumentException ignored) {
        }

        assertFalse(lab.exists(new Craftsman("Mr. 111", 100, 10)));

        lab.removeCraftsman(craftsmen.get(50));
        assertFalse(lab.exists(craftsmen.get(50)));

        try {
            lab.removeCraftsman(new Craftsman("Mr. 111", 100, 10));
            fail();
        } catch (IllegalArgumentException ignored) {
        }
    }
}

