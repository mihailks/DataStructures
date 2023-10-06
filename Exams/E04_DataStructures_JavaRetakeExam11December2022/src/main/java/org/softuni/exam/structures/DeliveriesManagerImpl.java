package org.softuni.exam.structures;

import org.softuni.exam.entities.Deliverer;
import org.softuni.exam.entities.Package;

import java.util.*;
import java.util.stream.Collectors;

public class DeliveriesManagerImpl implements DeliveriesManager {
    Map<String, Deliverer> deliverers;
    Map<String, Package> packages;
    Map<String, List<Package>> deliverersAndPackages;
    Map<String, Package> noDelivererPackages;

    public DeliveriesManagerImpl() {
        this.deliverers = new LinkedHashMap<>();
        this.packages = new LinkedHashMap<>();
        this.deliverersAndPackages = new LinkedHashMap<>();
        this.noDelivererPackages = new LinkedHashMap<>();

    }

    @Override
    public void addDeliverer(Deliverer deliverer) {
        this.deliverers.put(deliverer.getId(), deliverer);
        this.deliverersAndPackages.putIfAbsent(deliverer.getId(), new ArrayList<>());
    }

    @Override
    public void addPackage(Package _package) {
        this.packages.put(_package.getId(), _package);
        this.noDelivererPackages.put(_package.getId(), _package);
    }

    @Override
    public boolean contains(Deliverer deliverer) {
        return this.deliverers.containsKey(deliverer.getId());
    }

    @Override
    public boolean contains(Package _package) {
        return this.packages.containsKey(_package.getId());
    }

    @Override
    public Iterable<Deliverer> getDeliverers() {
        return this.deliverers.values();
    }

    @Override
    public Iterable<Package> getPackages() {
        return this.packages.values();
    }

    @Override
    public void assignPackage(Deliverer deliverer, Package _package) throws IllegalArgumentException {
        if (!deliverers.containsKey(deliverer.getId()) || !packages.containsKey(_package.getId())) {
            throw new IllegalArgumentException();
        }
        this.deliverersAndPackages.get(deliverer.getId()).add(_package);
        this.noDelivererPackages.remove(_package.getId());
    }

    @Override
    public Iterable<Package> getUnassignedPackages() {
        return this.noDelivererPackages.values();
    }

    @Override
    public Iterable<Package> getPackagesOrderedByWeightThenByReceiver() {
        return packages.values()
                .stream()
                .sorted(Comparator.comparing(Package::getWeight).reversed()
                        .thenComparing(Package::getReceiver))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Deliverer> getDeliverersOrderedByCountOfPackagesThenByName() {
        return deliverers.values()
                .stream()
                .sorted((d1, d2) -> {

                    int first = deliverersAndPackages.get(d1.getId()).size();
                    int second = deliverersAndPackages.get(d2.getId()).size();

                    int result = Integer.compare(second, first);
                    if (result == 0) {
                        result = d1.getName().compareTo(d2.getName());
                    }

                    return result;
                }).collect(Collectors.toList());
    }
}
