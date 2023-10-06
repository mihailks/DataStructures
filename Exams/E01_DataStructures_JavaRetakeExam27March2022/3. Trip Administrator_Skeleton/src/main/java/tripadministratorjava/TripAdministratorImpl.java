package tripadministratorjava;

import java.util.*;
import java.util.stream.Collectors;

public class TripAdministratorImpl implements TripAdministrator {
    private Map<String, Company> companies;
    private Map<String, List<Trip>> companiesWithTrips;
    private Map<String, Trip> trips;

    public TripAdministratorImpl() {
        companies = new LinkedHashMap<>();
        companiesWithTrips = new LinkedHashMap<>();
        trips = new LinkedHashMap<>();
    }

    @Override
    public void addCompany(Company c) {
        if (companies.containsKey(c.name)) {
            throw new IllegalArgumentException();
        }
        companies.put(c.name, c);
        companiesWithTrips.putIfAbsent(c.name, new ArrayList<>());
    }

    @Override
    public void addTrip(Company c, Trip t) {
        if (!companies.containsKey(c.name)) {
            throw new IllegalArgumentException();
        }

        if (companiesWithTrips.get(c.name).size() > c.tripOrganizationLimit) {
            throw new IllegalArgumentException();
        }

        if (trips.containsKey(t.id)) {
            throw new IllegalArgumentException();
        }

        trips.put(t.id, t);
        companiesWithTrips.get(c.name).add(t);
    }

    @Override
    public boolean exist(Company c) {
        return companies.containsKey(c.name);
    }

    @Override
    public boolean exist(Trip t) {
        return trips.containsKey(t.id);
    }

    @Override
    public void removeCompany(Company c) {
        if (!companies.containsKey(c.name)) {
            throw new IllegalArgumentException();
        }
        List<Trip> tripsToRemove = companiesWithTrips.get(c.name);
        companies.remove(c.name);
        companiesWithTrips.remove(c.name);
        if (tripsToRemove != null && !tripsToRemove.isEmpty()) {
            for (Trip trip : tripsToRemove) {
                trips.remove(trip.id);
            }
        }
    }

    @Override
    public Collection<Company> getCompanies() {
        return companies.values();
    }

    @Override
    public Collection<Trip> getTrips() {
//        return trips.values();
        return companiesWithTrips
                .values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }


    @Override
    public void executeTrip(Company c, Trip t) {
        if (!exist(c) || !exist(t)) {
            throw new IllegalArgumentException();
        }

        if (companiesWithTrips.get(c.name).contains(t)) {
            companiesWithTrips.get(c.name).remove(t);
            trips.remove(t.id);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Collection<Company> getCompaniesWithMoreThatNTrips(int n) {
//        List<Company> result = new ArrayList<>();
//        for (Map.Entry<String, List<Trip>> c : companiesWithTrips.entrySet()) {
//            if (c.getValue().size() > n) {
//                result.add(companies.get(c.getKey()));
//            }
//        }
//        return result;
        return getCompanies().stream()
                .filter(company -> companiesWithTrips.get(company.name).size()>n)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Trip> getTripsWithTransportationType(Transportation t) {
        return trips.values()
                .stream()
                .filter(trip -> trip.transportation.equals(t))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Trip> getAllTripsInPriceRange(int lo, int hi) {
        return trips.values()
                .stream()
                .filter(trips -> trips.price <= hi)
                .filter(trips -> trips.price >= lo)
                .collect(Collectors.toList());
    }
}
