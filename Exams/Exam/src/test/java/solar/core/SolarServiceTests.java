package solar.core;

import org.junit.Before;
import org.junit.Test;
import solar.models.Inverter;
import solar.models.PVModule;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class SolarServiceTests {
    private SolarService service;

    private final Inverter inverter = new Inverter("initial", 5);

    private final PVModule pvModule = new PVModule(10);

    @Before
    public void setup() {
        service = new SolarServiceImpl();
    }

    @Test
    public void addInverter() {
        service.addInverter(inverter);

        assertTrue(service.containsInverter(inverter.id));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addSameInverterTwice() {
        service.addInverter(inverter);
        service.addInverter(inverter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addArrayForMissingInverter() {
        service.addArray(inverter, "first");
    }

    @Test
    public void addPanel() {
        service.addInverter(inverter);
        service.addArray(inverter, "first");

        service.addPanel(inverter, "first", pvModule);

        assertTrue(service.isPanelConnected(pvModule));
    }

    @Test
    public void replaceModule() {
        service.addInverter(inverter);
        service.addArray(inverter, "first");
        service.addPanel(inverter, "first", pvModule);

        PVModule secondModule = new PVModule(5);

        service.replaceModule(pvModule, secondModule);

        assertNull(service.getInverterByPanel(pvModule));
        assertEquals(inverter, service.getInverterByPanel(secondModule));

        assertFalse(service.isPanelConnected(pvModule));
        assertTrue(service.isPanelConnected(secondModule));
    }
}
