package core;

import models.Package;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class PackageManagerImpl implements PackageManager {
    private Map<String, Package> packagesById = new LinkedHashMap<>();
    private Set<Package> packagesSet = new HashSet<>();
    private Map<Package, List<Package>> dependencies = new LinkedHashMap<>();

    @Override
    public void registerPackage(Package _package) {
        int size = packagesSet.size();

        packagesSet.add(_package);

        if (size == packagesSet.size()) {
            throw new IllegalArgumentException();
        }

        packagesById.put(_package.getId(), _package);
        dependencies.put(_package, new ArrayList<>());
    }

    @Override
    public void removePackage(String packageId) {
        Package result = this.packagesById.remove(packageId);

        if (result == null) {
            throw new IllegalArgumentException();
        }

        this.packagesSet.remove(result);
        this.dependencies.remove(result);

    }

    @Override
    public void addDependency(String packageId, String dependencyId) {
        if (!this.packagesById.containsKey(packageId) || !this.packagesById.containsKey(dependencyId)) {
            throw new IllegalArgumentException();
        }
        dependencies.get(this.packagesById.get(packageId)).add(this.packagesById.get(dependencyId));
    }

    @Override
    public boolean contains(Package _package) {
        return packagesById.containsKey(_package.getId());
    }

    @Override
    public int size() {
        return packagesById.size();
    }

    @Override
    public Iterable<Package> getDependants(Package _package) {
        return dependencies.get(_package);
    }

    @Override
    public Iterable<Package> getIndependentPackages() {
        return this.packagesSet.stream()
                .filter(p -> {
                    return !this.dependencies.containsKey(p);
                })
                .sorted((l, r) -> {
                    LocalDateTime leftReleaseDate = l.getReleaseDate();
                    LocalDateTime rightReleaseDate = r.getReleaseDate();


                    if (leftReleaseDate != rightReleaseDate) {
                        return rightReleaseDate.compareTo(leftReleaseDate);
                    }

                    return l.getVersion().compareTo(r.getVersion());
                })
                .collect(Collectors.toList());
    }


    @Override
    public Iterable<Package> getOrderedPackagesByReleaseDateThenByVersion() {
        return this.packagesById.values()
                .stream()
                .sorted((l, r) -> {
                    LocalDateTime leftDate = l.getReleaseDate();
                    LocalDateTime rightDate = r.getReleaseDate();

                    if (leftDate != rightDate) {
                        return rightDate.compareTo(leftDate);
                    } else {
                        return l.getVersion().compareTo(r.getVersion());
                    }
                })
                .collect(Collectors.toList());
    }
}
