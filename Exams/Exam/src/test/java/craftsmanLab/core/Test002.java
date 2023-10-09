package craftsmanLab.core;

import craftsmanLab.core.*;
import craftsmanLab.models.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class Test002 {
    private CraftsmanLab lab;

    private final ApartmentRenovation job = new ApartmentRenovation("varna1", 100, 10, LocalDate.now());

    @Before
    public void setup() {
        this.lab = new CraftsmanLabImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addApartmentThrowsExceptionWhenAddingSameApartment() {
        lab.addApartment(job);
        lab.addApartment(job);
    }
}

