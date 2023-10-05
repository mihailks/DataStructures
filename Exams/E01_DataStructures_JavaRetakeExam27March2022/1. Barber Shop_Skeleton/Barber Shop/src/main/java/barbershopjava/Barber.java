package barbershopjava;

public class Barber {

    public String name;
    public int haircutPrice;
    public int stars;

    public Barber(String name, int haircutPrice, int stars) {
        this.name = name;
        this.haircutPrice = haircutPrice;
        this.stars = stars;
    }

    public String getName() {
        return name;
    }

    public Barber setName(String name) {
        this.name = name;
        return this;
    }

    public int getHaircutPrice() {
        return haircutPrice;
    }

    public Barber setHaircutPrice(int haircutPrice) {
        this.haircutPrice = haircutPrice;
        return this;
    }

    public int getStars() {
        return stars;
    }

    public Barber setStars(int stars) {
        this.stars = stars;
        return this;
    }
}
