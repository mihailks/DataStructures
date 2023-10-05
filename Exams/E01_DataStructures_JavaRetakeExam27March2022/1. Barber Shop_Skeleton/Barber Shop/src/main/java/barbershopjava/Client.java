package barbershopjava;

public class Client {

    public String name;
    public int age;
    public Gender gender;
    public Barber barber;

    public Client(String name, int age, Gender gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public Client setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Client setAge(int age) {
        this.age = age;
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public Client setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public Barber getBarber() {
        return barber;
    }

    public Client setBarber(Barber barber) {
        this.barber = barber;
        return this;
    }
}
