package mlm.models;

public class Seller {
    public String id;
    public int earnings;
    String parentId;

    int sales = 0;

    public Seller(String id) {
        this.id = id;
        earnings = 0;
    }

    public String getId() {
        return id;
    }

    public Seller setId(String id) {
        this.id = id;
        return this;
    }

    public int getEarnings() {
        return earnings;
    }

    public Seller setEarnings(int earnings) {
        this.earnings = earnings;
        return this;
    }

    public int getSales() {
        return sales;
    }

    public Seller setSales(int sales) {
        this.sales = sales;
        return this;
    }

    public String getParentId() {
        return parentId;
    }

    public Seller setParentId(String parentId) {
        this.parentId = parentId;
        return this;
    }
}
