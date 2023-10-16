package raceManager.models;

import java.util.Objects;

public class Athlete {
    public String name;
    public int age;
    public boolean hasFinished;

    boolean hasStarted;


    public Athlete(String name, int age) {
        this.name = name;
        this.age = age;
        this.hasFinished = false;
    }

    public String getName() {
        return name;
    }

    public Athlete setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Athlete setAge(int age) {
        this.age = age;
        return this;
    }

    public boolean isHasStarted() {
        return hasStarted;
    }

    public Athlete setHasStarted(boolean hasStarted) {
        this.hasStarted = hasStarted;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Athlete athlete = (Athlete) o;
        return age == athlete.age && Objects.equals(name, athlete.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
