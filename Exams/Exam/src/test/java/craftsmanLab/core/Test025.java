package craftsmanLab.core;

import craftsmanLab.core.*;
import craftsmanLab.models.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class Test025 {

    private CraftsmanLab lab;

    @Before
    public void setup() {
        this.lab = new CraftsmanLabImpl();
    }

    @Test
    public void addApartments() {
        verifyCorrectness();

        lab = new CraftsmanLabImpl();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            this.lab.addApartment(new ApartmentRenovation(String.valueOf(i), 10.5 * i, 10 * i, LocalDate.now()));
        }
        long stop = System.currentTimeMillis();

        assertTrue(stop - start < 50);
    }

    private void verifyCorrectness() {
        List<ApartmentRenovation> apartments = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            ApartmentRenovation job = new ApartmentRenovation("varna" + i, 100, 10, LocalDate.now());

            apartments.add(job);
            lab.addApartment(job);
        }

        assertTrue(lab.exists(apartments.get(50)));

        try {
            lab.addApartment(apartments.get(50));
            fail();
        } catch (IllegalArgumentException ignored) {
        }

        assertFalse(lab.exists(new ApartmentRenovation("sofia101", 100, 10, LocalDate.now())));
    }
}

