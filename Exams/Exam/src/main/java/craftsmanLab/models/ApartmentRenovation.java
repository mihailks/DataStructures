package craftsmanLab.models;

import java.time.LocalDate;

public class ApartmentRenovation {
    public String address;
    public double area;
    public double workHoursNeeded;
    public LocalDate deadline;

    public ApartmentRenovation(String address, double area, double workHoursNeeded, LocalDate deadline) {
        this.address = address;
        this.area = area;
        this.workHoursNeeded = workHoursNeeded;
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "ApartmentRenovation{" +
                "address='" + address + '\'' +
                ", area=" + area +
                ", workHoursNeeded=" + workHoursNeeded +
                ", deadline=" + deadline +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public ApartmentRenovation setAddress(String address) {
        this.address = address;
        return this;
    }

    public double getArea() {
        return area;
    }

    public ApartmentRenovation setArea(double area) {
        this.area = area;
        return this;
    }

    public double getWorkHoursNeeded() {
        return workHoursNeeded;
    }

    public ApartmentRenovation setWorkHoursNeeded(double workHoursNeeded) {
        this.workHoursNeeded = workHoursNeeded;
        return this;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public ApartmentRenovation setDeadline(LocalDate deadline) {
        this.deadline = deadline;
        return this;
    }
}
