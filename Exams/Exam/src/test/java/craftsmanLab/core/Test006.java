package craftsmanLab.core;

import craftsmanLab.core.*;
import craftsmanLab.models.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class Test006 {
    private CraftsmanLab lab;


    @Before
    public void setup() {
        this.lab = new CraftsmanLabImpl();
    }

    @Test
    public void addCraftsmanCanHandleMultipleValues() {
        List<Craftsman> craftsmen = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Craftsman craftsman = new Craftsman("Bai " + i, 100, 0);

            craftsmen.add(craftsman);
            lab.addCraftsman(craftsman);
        }

        assertTrue(lab.exists(craftsmen.get(50)));
    }
}

