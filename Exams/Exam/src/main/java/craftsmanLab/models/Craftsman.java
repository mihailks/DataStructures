package craftsmanLab.models;

public class Craftsman {
    public String name;
    public double hourlyRate;
    public double totalEarnings;

    public Craftsman(String name, double hourlyRate, double totalEarnings) {
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.totalEarnings = totalEarnings;
    }

    @Override
    public String toString() {
        return "Craftsman{" +
                "name='" + name + '\'' +
                ", hourlyRate=" + hourlyRate +
                ", totalEarnings=" + totalEarnings +
                '}';
    }

    public String getName() {
        return name;
    }

    public Craftsman setName(String name) {
        this.name = name;
        return this;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public Craftsman setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
        return this;
    }

    public double getTotalEarnings() {
        return totalEarnings;
    }

    public Craftsman setTotalEarnings(double totalEarnings) {
        this.totalEarnings = totalEarnings;
        return this;
    }
}
