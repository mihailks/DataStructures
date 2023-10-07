package org.softuni.exam.structures;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.softuni.exam.entities.Airline;
import org.softuni.exam.entities.Flight;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

public class AirlinesManagerTests {
    private interface InternalTest {
        void execute();
    }

    private AirlinesManager airlinesManager;

    private Airline getRandomAirline() {
        return new Airline(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                Math.min(1, Math.random() * 1_000_000_000));
    }

    private Flight getRandomFlight() {
        return new Flight(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                ((Math.random() * 1_000) < 500));
    }

    @Before
    public void setup() {
        this.airlinesManager = new AirlinesManagerImpl();
    }

    public void performCorrectnessTesting(InternalTest[] methods) {
        Arrays.stream(methods)
                .forEach(method -> {
                    this.airlinesManager = new AirlinesManagerImpl();

                    try {
                        method.execute();
                    } catch (IllegalArgumentException ignored) { }
                });

        this.airlinesManager = new AirlinesManagerImpl();
    }

    // Correctness Tests

    @Test
    public void testContains_WithExistentAirline_ShouldReturnTrue() {
        Airline airline = getRandomAirline();
        this.airlinesManager.addAirline(airline);

        assertTrue(this.airlinesManager.contains(airline));
    }

    @Test
    public void testContains_WithNonExistentAirline_ShouldReturnFalse() {
        Airline airline = getRandomAirline();
        this.airlinesManager.addAirline(airline);

        assertFalse(this.airlinesManager.contains(getRandomAirline()));
    }

    @Test
    public void testContains_WithExistentFlight_ShouldReturnTrue() {
        Airline airline = getRandomAirline();
        Flight flight = getRandomFlight();
        this.airlinesManager.addAirline(airline);
        this.airlinesManager.addFlight(airline, flight);

        assertTrue(this.airlinesManager.contains(flight));
    }

    @Test
    public void testContains_WithNonExistentFlight_ShouldReturnFalse() {
        Airline airline = getRandomAirline();
        Flight flight = getRandomFlight();
        this.airlinesManager.addAirline(airline);
        this.airlinesManager.addFlight(airline, flight);

        assertFalse(this.airlinesManager.contains(getRandomFlight()));
    }

    @Test
    public void testDeleteAirline_WithNoData_ShouldProduceCorrectResults() {
        Airline airline = getRandomAirline();
        this.airlinesManager.addAirline(getRandomAirline());

        // Little bit of hacks
        try {
            this.airlinesManager.deleteAirline(airline);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }

        assertTrue(false);
    }

    @Test
    public void testAllFlights_WithData_ShouldReturnCorrectResults() {
        Airline airline = getRandomAirline();

        Flight flight1 = getRandomFlight();
        Flight flight2 = getRandomFlight();
        Flight flight3 = getRandomFlight();

        this.airlinesManager.addAirline(airline);
        this.airlinesManager.addFlight(airline, flight1);
        this.airlinesManager.addFlight(airline, flight2);
        this.airlinesManager.addFlight(airline, flight3);

        Set<Flight> set =
                StreamSupport.stream(this.airlinesManager.getAllFlights().spliterator(), false)
                        .collect(Collectors.toSet());

        assertEquals(set.size(), 3);
        assertTrue(set.contains(flight1));
        assertTrue(set.contains(flight2));
        assertTrue(set.contains(flight3));
    }

    // Performance Tests

    @Test
    public void testContainsAirline_With100000Results_ShouldPassQuickly() {
        this.performCorrectnessTesting(new InternalTest[] {
                this::testContains_WithExistentAirline_ShouldReturnTrue,
                this::testContains_WithNonExistentAirline_ShouldReturnFalse
        });

        int count = 100000;

        Airline airline = null;

        for (int i = 0; i < count; i++)
        {
            Airline airlineToAdd = getRandomAirline();

            this.airlinesManager.addAirline(airlineToAdd);

            if(i == count / 2) {
                airline = airlineToAdd;
            }
        }

        long start = System.currentTimeMillis();

        this.airlinesManager.contains(airline);

        long stop = System.currentTimeMillis();

        long elapsedTime = stop - start;

        assertTrue(elapsedTime <= 5);
    }

    @Test
    public void testContainsFlight_With100000Results_ShouldPassQuickly() {
        this.performCorrectnessTesting(new InternalTest[] {
                this::testContains_WithExistentFlight_ShouldReturnTrue,
                this::testContains_WithNonExistentFlight_ShouldReturnFalse
        });

        int count = 100000;


        Airline airline = getRandomAirline();
        Airline airline2 = getRandomAirline();
        Airline airline3 = getRandomAirline();
        this.airlinesManager.addAirline(airline);
        this.airlinesManager.addAirline(airline2);
        this.airlinesManager.addAirline(airline3);

        Flight flight = null;

        for (int i = 0; i < count; i++)
        {
            Flight flightToAdd = getRandomFlight();

            if(i < 10000) {
                this.airlinesManager.addFlight(airline, flightToAdd);
            } else if(i < count / 2) {
                this.airlinesManager.addFlight(airline2, flightToAdd);
            } else {
                this.airlinesManager.addFlight(airline3, flightToAdd);
            }

            if(i == count / 2) {
                flight = flightToAdd;
            }
        }

        long start = System.currentTimeMillis();

        this.airlinesManager.contains(flight);

        long stop = System.currentTimeMillis();

        long elapsedTime = stop - start;

        assertTrue(elapsedTime <= 5);
    }
    @Test
    public void testGetAirlinesOrderedByRatingThenByCountOfFlightsThenByNameShouldReturnSortedCorrectlyCollection() {
        Airline airline_1 = new Airline(UUID.randomUUID().toString(), "101", 1.5);
        Airline airline_2 = new Airline(UUID.randomUUID().toString(), "102", 1.5);
        Airline airline_3 = new Airline(UUID.randomUUID().toString(), "103", 1.8);
        Airline airline_4 = new Airline(UUID.randomUUID().toString(), "104", 1.1);
        Airline airline_5 = new Airline(UUID.randomUUID().toString(), "105", 1.2);
        Airline airline_6 = new Airline(UUID.randomUUID().toString(), "106", 1.2);

        Flight flight_1 = getRandomFlight();

        this.airlinesManager.addAirline(airline_6);
        this.airlinesManager.addAirline(airline_5);
        this.airlinesManager.addAirline(airline_4);
        this.airlinesManager.addAirline(airline_3);
        this.airlinesManager.addAirline(airline_2);
        this.airlinesManager.addAirline(airline_1);

        this.airlinesManager.addFlight(airline_2, flight_1);

        String[] expected = new String[]{"103", "102", "101", "105", "106", "104"};

        Iterable<Airline> airlineIterable = this.airlinesManager.getAirlinesOrderedByRatingThenByCountOfFlightsThenByName();
        int index = 0;
        for (Airline airline : airlineIterable) {
            Assert.assertEquals(expected[index++], airline.getName());
        }
    }

    @Test
    public void testGetAirlinesWithFlightsFromOriginToDestinationShouldReturnEmptyCollection() {
        Airline randomAirline = getRandomAirline();
        Flight flight_1 = getRandomFlight();
        Flight flight_2 = getRandomFlight();
        Flight flight_3 = getRandomFlight();

        flight_1.setOrigin("origin");
        flight_1.setCompleted(false);
        flight_2.setDestination("destination");
        flight_2.setCompleted(false);
        flight_3.setOrigin("origin");
        flight_3.setDestination("destination");
        flight_3.setCompleted(true);

        this.airlinesManager.addAirline(randomAirline);
        this.airlinesManager.addFlight(randomAirline, flight_1);
        this.airlinesManager.addFlight(randomAirline, flight_2);
        this.airlinesManager.addFlight(randomAirline, flight_3);

        Iterable<Airline> airlineIterable = this.airlinesManager.getAirlinesWithFlightsFromOriginToDestination("origin", "destination");
        Set<Airline> set = StreamSupport
                .stream(airlineIterable.spliterator(), false)
                .collect(Collectors.toSet());

        assertEquals(0, set.size());
    }

    @Test
    public void testGetAirlinesWithFlightsFromOriginToDestinationShouldReturnSortedCorrectlyCollection() {
        Airline airline_1 = getRandomAirline();
        Airline airline_2 = getRandomAirline();
        Airline airline_3 = getRandomAirline();
        Flight flight_1 = getRandomFlight();
        Flight flight_2 = getRandomFlight();
        Flight flight_3 = getRandomFlight();
        Flight flight_4 = getRandomFlight();
        Flight flight_5 = getRandomFlight();

        flight_1.setOrigin("origin");
        flight_1.setCompleted(false);
        flight_2.setDestination("destination");
        flight_2.setCompleted(false);
        flight_3.setOrigin("origin");
        flight_3.setDestination("destination");
        flight_3.setCompleted(false);
        flight_4.setOrigin("origin");
        flight_4.setDestination("destination");
        flight_4.setCompleted(false);
        flight_5.setOrigin("origin");
        flight_5.setDestination("destination");
        flight_5.setCompleted(true);

        this.airlinesManager.addAirline(airline_1);
        this.airlinesManager.addAirline(airline_2);
        this.airlinesManager.addAirline(airline_3);
        this.airlinesManager.addFlight(airline_2, flight_1);
        this.airlinesManager.addFlight(airline_2, flight_2);
        this.airlinesManager.addFlight(airline_2, flight_3);
        this.airlinesManager.addFlight(airline_2, flight_4);

        Iterable<Airline> airlineIterable = this.airlinesManager.getAirlinesWithFlightsFromOriginToDestination("origin", "destination");
        int collectionSize = 0;
        for (Airline airline : airlineIterable) {
            collectionSize++;
            Assert.assertEquals(airline_2, airline);
        }

        Assert.assertEquals(1, collectionSize);
    }

}
