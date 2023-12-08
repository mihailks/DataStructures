package models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Package {
    private String id;

    private String name;

    private String version;

    private LocalDateTime releaseDate;

    public Package(String id, String name, String version, LocalDateTime releaseDate) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.releaseDate = releaseDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Package aPackage = (Package) o;
        return Objects.equals(id, aPackage.id) && Objects.equals(name, aPackage.name) && Objects.equals(version, aPackage.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, version);
    }
}
