package craftsmanLab.core;

import craftsmanLab.core.*;
import craftsmanLab.models.*;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Test030 {

    private CraftsmanLab lab;

    @Before
    public void setup() {
        this.lab = new CraftsmanLabImpl();
    }

    @Test
    public void getLeastProfitable() {
        verifyCorrectness();
        lab = new CraftsmanLabImpl();

        for (int i = 0; i < 10000; i++) {
            this.lab.addCraftsman(new Craftsman(String.valueOf(i), 10 * i, 10 + i));
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            this.lab.getLeastProfitable();
        }
        long stop = System.currentTimeMillis();

        assertTrue(stop - start < 15);
    }

    private void verifyCorrectness() {
        Craftsman craftsman1 = new Craftsman("Bai Pesho", 100, 0);
        Craftsman craftsman2 = new Craftsman("Bai Gosho", 10, 950);

        ApartmentRenovation apart1 = new ApartmentRenovation("plovdi1", 100, 10, LocalDate.now());
        ApartmentRenovation apart2 = new ApartmentRenovation("plovdi2", 100, 10, LocalDate.now());
        ApartmentRenovation apart3 = new ApartmentRenovation("plovdi3", 100, 10, LocalDate.now());

        lab.addCraftsman(craftsman1);
        lab.addCraftsman(craftsman2);
        lab.addApartment(apart1);
        lab.addApartment(apart2);
        lab.addApartment(apart3);

        lab.assignRenovations();

        assertEquals(craftsman1.totalEarnings, 1000.0, 0);
        assertEquals(craftsman2.totalEarnings, 1050.0, 0);

        assertEquals(lab.getContractor(apart1), craftsman1);
        assertEquals(lab.getContractor(apart2), craftsman2);
//        assertEquals(lab.getContractor(apart3), craftsman1);

        assertEquals(lab.getLeastProfitable(), craftsman2);
    }
}
