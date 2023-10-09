package craftsmanLab.core;

import craftsmanLab.core.*;
import craftsmanLab.models.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class Test004 {
    private CraftsmanLab lab;

    private final Craftsman craftsman = new Craftsman("Bai Stavri", 10, 0);

    @Before
    public void setup() {
        this.lab = new CraftsmanLabImpl();
    }

    @Test
    public void addCraftsmanWorks() {
        lab.addCraftsman(craftsman);

        assertTrue(lab.exists(craftsman));
    }
}

