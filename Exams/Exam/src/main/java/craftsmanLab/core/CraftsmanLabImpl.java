package craftsmanLab.core;

import craftsmanLab.models.ApartmentRenovation;
import craftsmanLab.models.Craftsman;

import java.util.*;
import java.util.stream.Collectors;

public class CraftsmanLabImpl implements CraftsmanLab {

    private final Map<String, ApartmentRenovation> apartments;
    private final Map<String, Craftsman> craftsmen;
    private final PriorityQueue<Craftsman> craftsmenByProfit;
    private final Map<String, Craftsman> apartmentRenovator;


    public CraftsmanLabImpl() {
        this.apartments = new LinkedHashMap<>();
        this.craftsmen = new HashMap<>();
        this.craftsmenByProfit = new PriorityQueue<>(Comparator.comparingDouble(c -> c.totalEarnings));
        this.apartmentRenovator = new HashMap<>();
    }

    @Override
    public void addApartment(ApartmentRenovation job) {
        if (exists(job)) {
            throw new IllegalArgumentException();
        }

        apartments.put(job.address, job);
    }

    @Override
    public void addCraftsman(Craftsman craftsman) {
        if (exists(craftsman)) {
            throw new IllegalArgumentException();
        }

        craftsmen.put(craftsman.name, craftsman);
        craftsmenByProfit.add(craftsman);
    }

    @Override
    public boolean exists(ApartmentRenovation job) {
        return apartments.containsKey(job.address);
    }

    @Override
    public boolean exists(Craftsman craftsman) {
        return craftsmen.containsKey(craftsman.name);
    }

    @Override
    public void removeCraftsman(Craftsman craftsman) {
        if (!exists(craftsman)) {
            throw new IllegalArgumentException();
        }

        if (apartmentRenovator.containsValue(craftsman)) {
            throw new IllegalArgumentException();
        }

        craftsmenByProfit.remove(craftsman);
        craftsmen.remove(craftsman.name);
    }

    @Override
    public Collection<Craftsman> getAllCraftsmen() {
        return craftsmen.values();
    }

    @Override
    public void assignRenovations() {
        for (ApartmentRenovation job : apartments.values()) {
            if (apartmentRenovator.containsKey(job.address)) {
                continue;
            }

            Craftsman doingRepair = craftsmenByProfit.poll();

            doingRepair.totalEarnings += job.workHoursNeeded * doingRepair.hourlyRate;

            craftsmenByProfit.offer(doingRepair);
            apartmentRenovator.put(job.address, doingRepair);
        }
    }

        @Override
        public Craftsman getContractor(ApartmentRenovation job) {
            if (!apartmentRenovator.containsKey(job.address)) {
                throw new IllegalArgumentException();
            }

            return apartmentRenovator.get(job.address);
        }

    @Override
    public Craftsman getLeastProfitable() {
        if (craftsmenByProfit.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return craftsmenByProfit.peek();
    }

    @Override
    public Collection<ApartmentRenovation> getApartmentsByRenovationCost() {

        return apartments.values().stream()
                .sorted((l, r) -> {
                    Craftsman leftCraftsman = apartmentRenovator.get(l.address);
                    Craftsman rightCraftsman = apartmentRenovator.get(r.address);

                    double leftCost = leftCraftsman == null ?
                            l.workHoursNeeded : leftCraftsman.hourlyRate * l.workHoursNeeded;
                    double rightCost = rightCraftsman == null ?
                            r.workHoursNeeded : rightCraftsman.hourlyRate * r.workHoursNeeded;

                    return Double.compare(rightCost, leftCost);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ApartmentRenovation> getMostUrgentRenovations(int limit) {
        return apartments.values()
                .stream()
                .sorted(Comparator.comparing(c -> c.deadline))
                .limit(limit)
                .collect(Collectors.toList());
    }
}
