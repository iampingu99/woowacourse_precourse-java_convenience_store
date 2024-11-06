package store.model;

public class Product {
    private final String name;
    private final int price;
    private final int quantity;
    private final String promotion;

    public Product(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public static Product from(String record) {
        String[] fields = record.split(",");
        return new Product(
                fields[0],
                Integer.parseInt(fields[1]),
                Integer.parseInt(fields[2]),
                fields[3]);
    }
}
