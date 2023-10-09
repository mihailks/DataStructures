package craftsmanLab.core;

import craftsmanLab.core.*;
import craftsmanLab.models.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class Test007 {
    private CraftsmanLab lab;

    private final Craftsman craftsman = new Craftsman("Bai Stavri", 10, 0);

    @Before
    public void setup() {
        this.lab = new CraftsmanLabImpl();
    }

    @Test
    public void removeCraftsmanWorks() {
        lab.addCraftsman(craftsman);
        lab.removeCraftsman(craftsman);

        assertFalse(lab.exists(craftsman));
    }
}

