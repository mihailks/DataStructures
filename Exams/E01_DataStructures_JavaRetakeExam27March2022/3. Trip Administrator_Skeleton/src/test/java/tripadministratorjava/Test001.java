package tripadministratorjava;

import tripadministratorjava.Company;
import tripadministratorjava.Transportation;
import tripadministratorjava.Trip;
import tripadministratorjava.TripAdministratorImpl;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class Test001 {

    private TripAdministratorImpl tripAdministrations;

    private Company c1 = new Company("c1", 2);
    private Company c2 = new Company("c2", 2);
    private Company c3 = new Company("c3", 2);
    private Company c4 = new Company("c4", 2);
    private Company c10 = new Company("c10", 2);
    private Trip t1 = new Trip("t1", 10, Transportation.BUS, 100);
    private Trip t2 = new Trip("t2", 10, Transportation.BUS, 100);
    private Trip t3 = new Trip("t3", 10, Transportation.BUS, 100);
    private Trip t4 = new Trip("t4", 10, Transportation.BUS, 100);

    @Before
    public void Setup() {
        this.tripAdministrations = new TripAdministratorImpl();
        this.tripAdministrations.addCompany(c1);
        this.tripAdministrations.addCompany(c2);
        this.tripAdministrations.addCompany(c3);
        this.tripAdministrations.addCompany(c4);


        this.tripAdministrations.addTrip(c1, t1);
        this.tripAdministrations.addTrip(c3, t2);
        this.tripAdministrations.addTrip(c3, t3);
        this.tripAdministrations.addTrip(c3, t4);

    }

    @Test
    public void TestAddCompany() {
        assertTrue(this.tripAdministrations.exist(c1));
    }

    @Test
    public void testExecute() {
        tripAdministrations.executeTrip(c3, t2);
        tripAdministrations.executeTrip(c3, t3);
        tripAdministrations.executeTrip(c3, t4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteNoCompanyShouldThrow() {
        tripAdministrations.executeTrip(c10, t1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteNoTripShouldThrow() {
        tripAdministrations.executeTrip(c10, t1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExecuteNoTripInCompanyShouldThrow() {
        tripAdministrations.executeTrip(c3, t1);
    }

    @Test
    public void testRemoveCompany(){
        tripAdministrations.removeCompany(c3);
    }

    @Test
    public void testGetTips() {
        tripAdministrations.removeCompany(c3);
        assertEquals(1, tripAdministrations.getTrips().size());
    }
}