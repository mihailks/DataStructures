package craftsmanLab.core;

import craftsmanLab.core.*;
import craftsmanLab.models.*;
import org.junit.Before;
import org.junit.Test;

public class Test008 {
    private CraftsmanLab lab;

    private final Craftsman craftsman = new Craftsman("Bai Stavri", 10, 0);

    @Before
    public void setup() {
        this.lab = new CraftsmanLabImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void removingNonExistingCraftsmanThrowsException() {
        lab.removeCraftsman(craftsman);
    }
}

