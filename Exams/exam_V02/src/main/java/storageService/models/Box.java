package storageService.models;

import java.util.Objects;

public class Box {
    public String id;
    public int width;
    public int height;
    public int depth;

    public int getVolume() {
        return width * height * depth;
    }

    public Box(String id, int width, int height, int depth) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public String getId() {
        return id;
    }

    public Box setId(String id) {
        this.id = id;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public Box setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public Box setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getDepth() {
        return depth;
    }

    public Box setDepth(int depth) {
        this.depth = depth;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Box box = (Box) o;
        return Objects.equals(id, box.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
