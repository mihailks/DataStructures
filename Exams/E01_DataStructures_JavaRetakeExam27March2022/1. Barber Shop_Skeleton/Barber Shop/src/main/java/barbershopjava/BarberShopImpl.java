package barbershopjava;

import java.util.*;
import java.util.stream.Collectors;

public class BarberShopImpl implements BarberShop {
    Map<String, Barber> allBarbers = new HashMap<>();
    Map<String, Client> allClients = new HashMap<>();
    Map<String, List<Client>> clientsPerBarber = new HashMap<>();
    Map<String, Client> clientsWithNoBarber = new HashMap<>();

    public BarberShopImpl() {

    }

    @Override
    public void addBarber(Barber b) {
        if (allBarbers.containsKey(b.name)) {
            throw new IllegalArgumentException();
        }
        allBarbers.put(b.name, b);
        clientsPerBarber.putIfAbsent(b.name, new ArrayList<>());

    }

    @Override
    public void addClient(Client c) {
        if (allClients.containsKey(c.name)) {
            throw new IllegalArgumentException();
        }
        allClients.put(c.name, c);
        clientsWithNoBarber.put(c.name, c);
    }

    @Override
    public boolean exist(Barber b) {
        return allBarbers.containsKey(b.name);
    }

    @Override
    public boolean exist(Client c) {
        return allClients.containsKey(c.name);
    }

    @Override
    public Collection<Barber> getBarbers() {
//        return allBarbers
//                .values()
//                .stream()
//                .collect(Collectors.toList());
        return allBarbers.values();
    }

    @Override
    public Collection<Client> getClients() {
        return allClients.values();
    }

    @Override
    public void assignClient(Barber b, Client c) {
        if (!allBarbers.containsKey(b.name)) {
            throw new IllegalArgumentException();
        }
        if (!allClients.containsKey(c.name)) {
            throw new IllegalArgumentException();
        }

        c.barber = b;


        clientsPerBarber.get(b.name).add(c);

        clientsWithNoBarber.remove(c.name);
    }

    @Override
    public void deleteAllClientsFrom(Barber b) {
        if (!clientsPerBarber.containsKey(b.name)) {
            throw new IllegalArgumentException();
        }
        List<Client> ClientsToRemove = clientsPerBarber.remove(b.name);
        for (Client client : ClientsToRemove) {
            clientsWithNoBarber.put(client.name, client);
        }
    }

    @Override
    public Collection<Client> getClientsWithNoBarber() {
//        return clientsWithNoBarber.values();

        return getClients().stream()
                .filter(client -> client.barber == null)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Barber> getAllBarbersSortedWithClientsCountDesc() {
        return getBarbers()
                .stream()
                .sorted((b1, b2) -> {
                    int firstClients = clientsPerBarber.get(b1.name).size();
                    int secondClients = clientsPerBarber.get(b2.name).size();
                    return Integer.compare(secondClients, firstClients);
                }).collect(Collectors.toList());
    }

    @Override
    public Collection<Barber> getAllBarbersSortedWithStarsDescendingAndHaircutPriceAsc() {
//        return getBarbers()
//                .stream()
//                .sorted(Comparator.comparing(Barber::getStars).reversed()
//                        .thenComparing(Barber::getHaircutPrice))
//                .collect(Collectors.toList());
        return getBarbers()
                .stream()
                .sorted((b1, b2) -> {
                    int result = Integer.compare(b2.stars, b1.stars);
                    if (result == 0) {
                        result = Integer.compare(b1.haircutPrice, b2.haircutPrice);
                    }
                    return result;
                }).collect(Collectors.toList());
    }

    @Override
    public Collection<Client> getClientsSortedByAgeDescAndBarbersStarsDesc() {
//        return getClients()
//                .stream()
//                .sorted((c1, c2) -> Integer.compare(c2.age, c1.age))
//                .collect(Collectors.toList());

        return getClients()
                .stream()
                .sorted(Comparator.comparing(Client::getAge)
                        .thenComparing(client -> client.barber.stars).reversed())
                .collect(Collectors.toList());
    }
}
