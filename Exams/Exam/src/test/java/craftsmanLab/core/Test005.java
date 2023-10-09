package craftsmanLab.core;

import craftsmanLab.core.*;
import craftsmanLab.models.*;
import org.junit.Before;
import org.junit.Test;

public class Test005 {
    private CraftsmanLab lab;

    private final Craftsman craftsman = new Craftsman("Bai Stavri", 10, 0);

    @Before
    public void setup() {
        this.lab = new CraftsmanLabImpl();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addCraftsmanThrowsExceptionWhenAddingSameValue() {
        lab.addCraftsman(craftsman);
        lab.addCraftsman(craftsman);
    }
}
