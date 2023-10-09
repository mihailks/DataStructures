package craftsmanLab.core;

import craftsmanLab.core.*;
import craftsmanLab.models.*;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class Test028 {

    private CraftsmanLab lab;

    @Before
    public void setup() {
        this.lab = new CraftsmanLabImpl();
    }

    @Test
    public void assignRenovations() {
        verifyCorrectness();
        lab = new CraftsmanLabImpl();

        List<ApartmentRenovation> list = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            ApartmentRenovation current = new ApartmentRenovation(String.valueOf(i), 10.5 * i, 10 * i, LocalDate.now());
            list.add(current);
            this.lab.addApartment(current);
        }

        for (int i = 0; i < 10000; i++) {
            this.lab.addCraftsman(new Craftsman(String.valueOf(i), 10 * i, 10 + i));
        }

        long start = System.currentTimeMillis();

        lab.assignRenovations();

        long stop = System.currentTimeMillis();

        assertTrue(stop - start < 200);
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
    }
}
