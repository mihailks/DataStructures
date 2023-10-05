package tripadministratorjava;

import java.util.*;
import java.util.stream.Collectors;

public class TripAdministratorImpl implements TripAdministrator {
    private Map<String, Company> companies;
    private Map<String, List<Trip>> tripsAndCompanies;
    private Map<String, Trip> trips;

    public TripAdministratorImpl() {
        companies = new LinkedHashMap<>();
        tripsAndCompanies = new LinkedHashMap<>();
        trips = new LinkedHashMap<>();
    }

    @Override
    public void addCompany(Company c) {
        if (companies.containsKey(c.name)) {
            throw new IllegalArgumentException();
        }
        companies.put(c.name, c);
    }

    @Override
    public void addTrip(Company c, Trip t) {
        if (!companies.containsKey(c.name)) {
            throw new IllegalArgumentException();
        }

        tripsAndCompanies.putIfAbsent(c.name, new ArrayList<>());

        if (tripsAndCompanies.get(c.name).size() > c.tripOrganizationLimit) {
            throw new IllegalArgumentException();
        }

        if (trips.containsKey(t.id)) {
            throw new IllegalArgumentException();
        }

        trips.put(t.id, t);
        tripsAndCompanies.get(c.name).add(t);
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
        List<Trip> TripsToRemove = tripsAndCompanies.get(c.name);
        companies.remove(c.name);
        tripsAndCompanies.remove(c.name);

        for (Trip trip : TripsToRemove) {
            trips.remove(trip.id);
        }
    }

    @Override
    public Collection<Company> getCompanies() {
        return companies.values();
    }

    @Override
    public Collection<Trip> getTrips() {
        return trips.values();
//        return tripsAndCompanies
//                .values()
//                .stream()
//                .flatMap(List::stream)
//                .collect(Collectors.toList());
    }


    @Override
    public void executeTrip(Company c, Trip t) {
        if (!companies.containsKey(c.name)) {
            throw new IllegalArgumentException();
        }
        if (!trips.containsKey(t.id)) {
            throw new IllegalArgumentException();
        }

        List<Trip> currentCompanyTrips = tripsAndCompanies.get(c.name);

        Trip tripToExecute = null;

        int i = 0;
        for (; i < currentCompanyTrips.size(); i++) {
            if (currentCompanyTrips.get(i).id.equals(t.id)) {
                tripToExecute = currentCompanyTrips.get(i);
                tripsAndCompanies.get(c.name).remove(i);
                trips.remove(tripToExecute.id);
            }
        }
        if (tripToExecute == null) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Collection<Company> getCompaniesWithMoreThatNTrips(int n) {
        List<Company> result = new ArrayList<>();
        for (Map.Entry<String, List<Trip>> c : tripsAndCompanies.entrySet()) {
            if (c.getValue().size() > n) {
                result.add(companies.get(c.getKey()));
            }
        }
        return result;
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
