package solar.models;

public class PVModule {
    public int maxWattProduction;

    public PVModule(int maxWattProduction) {
        this.maxWattProduction = maxWattProduction;
    }

    public int getMaxWattProduction() {
        return maxWattProduction;
    }

    public PVModule setMaxWattProduction(int maxWattProduction) {
        this.maxWattProduction = maxWattProduction;
        return this;
    }
}
