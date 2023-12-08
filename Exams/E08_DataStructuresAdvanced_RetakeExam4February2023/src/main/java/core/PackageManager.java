package core;

import models.Package;

public interface PackageManager {
    void registerPackage(Package _package);

    void removePackage(String packageId);

    void addDependency(String packageId, String dependencyId);

    boolean contains(Package _package);

    int size();

    Iterable<Package> getDependants(Package _package);

    Iterable<Package> getIndependentPackages();

    Iterable<Package> getOrderedPackagesByReleaseDateThenByVersion();
}
