package craftsmanLab.core;

import craftsmanLab.core.*;
import craftsmanLab.models.*;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class Test026 {

    private CraftsmanLab lab;

    @Before
    public void setup() {
        this.lab = new CraftsmanLabImpl();
    }

    @Test
    public void apartmentsExist() {
        verifyCorrectness();
        lab = new CraftsmanLabImpl();

        List<ApartmentRenovation> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            ApartmentRenovation current = new ApartmentRenovation(String.valueOf(i), 10.5 * i, 10 * i, LocalDate.now());
            list.add(current);
            this.lab.addApartment(current);
        }
        Random random = new Random();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            this.lab.exists(list.get(random.nextInt(10000)));
        }
        long stop = System.currentTimeMillis();

        assertTrue(stop - start < 15);
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

