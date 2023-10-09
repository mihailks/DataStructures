package craftsmanLab.core;

import craftsmanLab.models.ApartmentRenovation;
import craftsmanLab.models.Craftsman;

import java.util.*;
import java.util.stream.Collectors;

public class CraftsmanLabImpl implements CraftsmanLab {

    //FIXME: needs more work
    Map<String, ApartmentRenovation> apartments;
    Map<String, Craftsman> craftsmans;
    Map<ApartmentRenovation, Craftsman> apartmentRenovationCraftsman;


    public CraftsmanLabImpl() {
        apartments = new LinkedHashMap<>();
        craftsmans = new HashMap<>();
        apartmentRenovationCraftsman = new LinkedHashMap<>();
    }

    @Override
    public void addApartment(ApartmentRenovation job) {
        if (apartments.containsKey(job.address)) {
            throw new IllegalArgumentException();
        }
        apartments.put(job.address, job);
        apartmentRenovationCraftsman.put(job, null);
    }

    @Override
    public void addCraftsman(Craftsman craftsman) {
        if (craftsmans.containsKey(craftsman.name)) {
            throw new IllegalArgumentException();
        }
        craftsmans.put(craftsman.name, craftsman);
    }

    @Override
    public boolean exists(ApartmentRenovation job) {
        return apartments.containsKey(job.address);
    }

    @Override
    public boolean exists(Craftsman craftsman) {
        return craftsmans.containsKey(craftsman.name);
    }

    @Override
    public void removeCraftsman(Craftsman craftsman) {
        if (!craftsmans.containsKey(craftsman.name)) {
            throw new IllegalArgumentException();
        }

        for (Craftsman current : apartmentRenovationCraftsman.values()) {
            if (current.equals(craftsman)) {
                throw new IllegalArgumentException();
            }
        }

        craftsmans.remove(craftsman.name);
    }

    @Override
    public Collection<Craftsman> getAllCraftsmen() {
        return craftsmans.values();
    }

    @Override
    public void assignRenovations() {
        List<Craftsman> craftsmanByMoney = craftsmans.values()
                .stream()
                .sorted(Comparator.comparing(Craftsman::getTotalEarnings))
                .collect(Collectors.toList());

        for (ApartmentRenovation currentAp : apartmentRenovationCraftsman.keySet()) {
            if (apartmentRenovationCraftsman.get(currentAp) == null) {
                int index = 0;
                while (index < craftsmanByMoney.size()) {
                    Craftsman craftsman = craftsmanByMoney.remove(0);
                    craftsman.totalEarnings += currentAp.workHoursNeeded * craftsman.getHourlyRate();
                    apartmentRenovationCraftsman.put(currentAp, craftsman);
                    index++;
                }
            }
        }
    }

    @Override
    public Craftsman getContractor(ApartmentRenovation job) {
        if (apartmentRenovationCraftsman.get(job) == null) {
            throw new IllegalArgumentException();
        }
        return apartmentRenovationCraftsman.get(job);
    }

    @Override
    public Craftsman getLeastProfitable() {
////        return craftsmans.values()
////                .stream()
////                .sorted(Comparator.comparing(Craftsman::getTotalEarnings).reverced())
////                .collect(Collectors.toList()).get(0);
//        double least = -10;
//        String resultName = null;
//        for (Craftsman current : craftsmans.values()) {
//            if (current.totalEarnings>least){
//                least = current.totalEarnings;
//                resultName = current.name;
//                break; // nope
//            }
//        }
//        return craftsmans.get(resultName);

        double least = Double.MAX_VALUE;
        String resultName = null;

        for (Craftsman current : craftsmans.values()) {
            if (current.totalEarnings < least) {
                least = current.totalEarnings;
                resultName = current.name;
                break; //
            }
        }

        return craftsmans.get(resultName);
    }

    @Override
    public Collection<ApartmentRenovation> getApartmentsByRenovationCost() {

        return apartmentRenovationCraftsman.keySet()
                .stream()
                .sorted((a1, a2) -> {

                    double first;
                    double second;

                    if (apartmentRenovationCraftsman.get(a1) == null) {
                        first = a1.workHoursNeeded;
                    } else {
                        first = apartmentRenovationCraftsman.get(a1).getHourlyRate() * a1.workHoursNeeded;
                    }

                    if (apartmentRenovationCraftsman.get(a2) == null) {
                        second = a2.workHoursNeeded;
                    } else {
                        second = apartmentRenovationCraftsman.get(a2).getHourlyRate() * a2.workHoursNeeded;
                    }
                    return Double.compare(second, first);
                }).collect(Collectors.toList());
    }

    @Override
    public Collection<ApartmentRenovation> getMostUrgentRenovations(int limit) {
        return apartments.values()
                .stream()
                .sorted(Comparator.comparing(ApartmentRenovation::getDeadline))
                .limit(limit)
                .collect(Collectors.toList());
    }
}
