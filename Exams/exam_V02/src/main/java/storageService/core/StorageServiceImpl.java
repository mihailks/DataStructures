package storageService.core;


import storageService.models.Box;
import storageService.models.StorageUnit;

import java.util.*;
import java.util.stream.Collectors;

public class StorageServiceImpl implements StorageService {

    Map<String, Box> boxes;
    Map<String, StorageUnit> storageUnits;
    Map<String, List<Box>> storageUnitAndBoxes;
    PriorityQueue<StorageUnit> storageUnitsByFillRate;

    int totalUnitsSpace = 0;
    int currentFreeSpace = 0;

    public StorageServiceImpl() {
        boxes = new HashMap<>();
        storageUnits = new HashMap<>();
        storageUnitAndBoxes = new HashMap<>();
        storageUnitsByFillRate = new PriorityQueue<>(Comparator.comparing(StorageUnit::getFreeSpace));
    }

    @Override
    public void rentStorage(StorageUnit unit) {

        if (storageUnits.containsKey(unit.getId())) {
            throw new IllegalArgumentException();
        }

        storageUnits.put(unit.getId(), unit);
        storageUnitAndBoxes.put(unit.getId(), new ArrayList<>());
        storageUnitsByFillRate.add(unit);


        totalUnitsSpace += unit.getTotalAvailableSpace();
        currentFreeSpace += unit.getTotalAvailableSpace();
    }

    @Override
    public void storeBox(Box box) {

        if (totalUnitsSpace == 0) { // no units
            throw new IllegalArgumentException();
        }

        if (boxes.containsKey(box.getId())) {
            throw new IllegalArgumentException();
        }

        int currentBoxVolume = box.getWidth() * box.getHeight() * box.getDepth();

        if (currentFreeSpace < currentBoxVolume) {  //if there are space at all
            throw new IllegalArgumentException();
        }

        StorageUnit unit = getMostAvailableSpaceUnit();


        if (unit.getFreeSpace() >= currentBoxVolume) {
            unit.setTotalUsedSpace(unit.getTotalUsedSpace() + currentBoxVolume);

            storageUnitAndBoxes.get(unit.id).add(box);

            boxes.put(box.getId(), box);

            currentFreeSpace -= currentBoxVolume;

        }
    }

    @Override
    public boolean isStored(Box box) {
        return boxes.containsKey(box.getId());
    }

    @Override
    public boolean isRented(StorageUnit unit) {
        return storageUnits.containsKey(unit.getId());
    }

    @Override
    public boolean contains(StorageUnit unit, String boxId) {
        if (!boxes.containsKey(boxId) || !storageUnits.containsKey(unit.getId())) {
            return false;
        }
        return storageUnitAndBoxes.get(unit.getId()).contains(boxes.get(boxId));
    }

    @Override
    public Box retrieve(StorageUnit unit, String boxId) {
        if (!boxes.containsKey(boxId)) {
            throw new IllegalArgumentException();
        }
        if (!storageUnits.containsKey(unit.getId())) {
            throw new IllegalArgumentException();
        }

        StorageUnit storageUnit = storageUnits.get(unit.getId());
        Box box = boxes.get(boxId);

        if (!storageUnitAndBoxes.get(unit.getId()).contains(box)) {
            throw new IllegalArgumentException();
        }

        boxes.remove(boxId);
        storageUnitAndBoxes.get(unit.getId()).remove(box);

        storageUnit.setTotalUsedSpace(storageUnit.getTotalUsedSpace() - box.getVolume());

        currentFreeSpace += box.getVolume();

        return box;
    }

    @Override
    public int getTotalFreeSpace() {
//        return storageUnits.values()
//                .stream()
//                .mapToInt(StorageUnit::getFreeSpace)
//                .sum();
        return currentFreeSpace;
    }

    @Override
    public StorageUnit getMostAvailableSpaceUnit() {

        if (storageUnits.isEmpty()) {
            throw new IllegalArgumentException();
        }
//        StorageUnit storageUnit;
//        storageUnit = storageUnitsByFillRate.peek();
//        return storageUnit;


//        return storageUnits.values()
//                .stream()
//                .sorted(Comparator.comparing(StorageUnit::getFreeSpace).reversed())
//                .collect(Collectors.toList()).get(0);


       return storageUnits.values()
               .stream()
               .sorted((u1, u2) -> Integer.compare(u2.getFreeSpace(), u1.getFreeSpace()))
               .collect(Collectors.toList()).get(0);

    }

    @Override
    public Collection<Box> getAllBoxesByVolume() {
        return boxes.values()
                .stream()
                .sorted((b1, b2) -> {
                    int result = Integer.compare(b1.getVolume(), b2.getVolume());
                    if (result == 0) {
                        result = Integer.compare(b2.getHeight(), b1.getHeight());
                    }
                    return result;
                })
                .collect(Collectors.toList());


    }

    @Override
    public Collection<StorageUnit> getAllUnitsByFillRate() {
//        return storageUnits.values()
//                .stream()
//                .sorted(Comparator.comparing(StorageUnit::freeSpacePercentage).reversed()
//                        .thenComparing(StorageUnit::getTotalAvailableSpace).reversed())
//                .collect(Collectors.toList());

        return storageUnits.values()
                .stream()
                .sorted((u1, u2) -> {
                    int result = Integer.compare(u2.freeSpacePercentage(), u1.freeSpacePercentage());
                    if (result == 0) {
                        result = Integer.compare(u2.totalAvailableSpace, u1.totalAvailableSpace);
                    }
                    return result;
                })
                .collect(Collectors.toList());

    }
}
