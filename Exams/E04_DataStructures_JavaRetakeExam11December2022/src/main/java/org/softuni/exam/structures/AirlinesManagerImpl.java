package org.softuni.exam.structures;

import org.softuni.exam.entities.Airline;
import org.softuni.exam.entities.Flight;

import java.util.*;
import java.util.stream.Collectors;

public class AirlinesManagerImpl implements AirlinesManager {
    Map<String, Airline> airlines;
    Map<String, Flight> flights;
    Map<String, List<Flight>> airlinesAndFlights;


    public AirlinesManagerImpl() {
        this.airlines = new HashMap<>();
        this.flights = new HashMap<>();
        this.airlinesAndFlights = new HashMap<>();
    }

    @Override
    public void addAirline(Airline airline) {
        this.airlines.put(airline.getId(), airline);
        airlinesAndFlights.put(airline.getId(), new ArrayList<>());
    }

    @Override
    public void addFlight(Airline airline, Flight flight) {
        if (!this.airlines.containsKey(airline.getId())) {
            throw new IllegalArgumentException();
        }
        flights.put(flight.getId(), flight);
        airlinesAndFlights.get(airline.getId()).add(flight);

    }

    @Override
    public boolean contains(Airline airline) {
        return airlines.containsKey(airline.getId());
    }

    @Override
    public boolean contains(Flight flight) {
        return flights.containsKey(flight.getId());
    }

    @Override
    public void deleteAirline(Airline airline) throws IllegalArgumentException {
        if (!this.airlines.containsKey(airline.getId())) {
            throw new IllegalArgumentException();
        }

        airlines.remove(airline.getId());
        List<Flight> flightsToRemove = airlinesAndFlights.get(airline.getId());
        airlinesAndFlights.remove(airline.getId());

        for (Flight flight : flightsToRemove) {
            flights.remove(flight.getId());
        }

    }

    @Override
    public Iterable<Flight> getAllFlights() {
        return flights.values();
    }

    @Override
    public Flight performFlight(Airline airline, Flight flight) throws IllegalArgumentException {
        if (!this.airlines.containsKey(airline.getId()) || !flights.containsKey(flight.getId())) {
            throw new IllegalArgumentException();
        }
        flight.setCompleted(true);
        return flight;
    }

    @Override
    public Iterable<Flight> getCompletedFlights() {
        return flights.values()
                .stream()
                .filter(Flight::isCompleted)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Flight> getFlightsOrderedByNumberThenByCompletion() {
        return flights.values()
                .stream()
                .sorted(Comparator.comparing(Flight::isCompleted)
                        .thenComparing(Flight::getNumber))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Airline> getAirlinesOrderedByRatingThenByCountOfFlightsThenByName() {

//        return airlines.values()
//                .stream()
//                .sorted(Comparator.comparing(Airline::getRating).reversed()
//                        .thenComparing((a1, a2) -> {
//                            int first = airlinesAndFlights.get(a1.getId()).size();
//                            int second = airlinesAndFlights.get(a2.getId()).size();
//                            int result = Integer.compare(second, first);
//                            if (result == 0) {
//                                result = a1.getName().compareTo(a2.getName());
//                            }
//                            return result;
//                        })).collect(Collectors.toList());

        return airlines.values()
                .stream()
                .sorted(Comparator.comparing(Airline::getRating).reversed()
                        .thenComparing((a1, a2) -> {
                            int first = airlinesAndFlights.get(a1.getId()).size();
                            int second = airlinesAndFlights.get(a2.getId()).size();
                            return Integer.compare(second, first);
                        }).thenComparing(Airline::getName))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Airline> getAirlinesWithFlightsFromOriginToDestination(String origin, String destination) {
        Set<Airline> result = new HashSet<>();

        airlinesAndFlights.forEach((k, v) -> {
            for (Flight flight : v) {
                if (!flight.isCompleted() && flight.getOrigin().equals(origin) && flight.getDestination().equals(destination)) {
                    result.add(airlines.get(k));
                }
            }
        });
        return result;
    }
}





