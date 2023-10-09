package craftsmanLab.core;

import craftsmanLab.core.*;
import craftsmanLab.models.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class Test003 {
    private CraftsmanLab lab;


    @Before
    public void setup() {
        this.lab = new CraftsmanLabImpl();
    }

    @Test
    public void addApartmentCanHandleMultipleApartments() {
        List<ApartmentRenovation> apartments = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            ApartmentRenovation job = new ApartmentRenovation("varna" + i, 100, 10, LocalDate.now());

            apartments.add(job);
            lab.addApartment(job);
        }

        assertTrue(lab.exists(apartments.get(50)));
    }
}
