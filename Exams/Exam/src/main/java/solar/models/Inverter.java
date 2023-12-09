package solar.models;

public class Inverter {
    public String id;
    public int maxPvArraysConnected;

    public Inverter(String id, int maxPvArraysConnected) {
        this.id = id;
        this.maxPvArraysConnected = maxPvArraysConnected;
    }

    public String getId() {
        return id;
    }

    public Inverter setId(String id) {
        this.id = id;
        return this;
    }

    public int getMaxPvArraysConnected() {
        return maxPvArraysConnected;
    }

    public Inverter setMaxPvArraysConnected(int maxPvArraysConnected) {
        this.maxPvArraysConnected = maxPvArraysConnected;
        return this;
    }
}
