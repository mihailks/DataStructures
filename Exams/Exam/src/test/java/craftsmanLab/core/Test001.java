package craftsmanLab.core;

import craftsmanLab.core.*;
import craftsmanLab.models.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertTrue;

public class Test001 {
    private CraftsmanLab lab;

    private final ApartmentRenovation job = new ApartmentRenovation("varna1", 100, 10, LocalDate.now());

    @Before
    public void setup() {
        this.lab = new CraftsmanLabImpl();
    }

    @Test
    public void addApartmentWorks() {
        lab.addApartment(job);

        assertTrue(lab.exists(job));
    }
}

