package store.model;

import java.text.NumberFormat;
import java.util.Objects;

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
        String promotion = null;
        if (!fields[3].equals("null")) {
            promotion = fields[3];
        }
        return new Product(
                fields[0],
                Integer.parseInt(fields[1]),
                Integer.parseInt(fields[2]),
                promotion);
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPromotion() {
        return promotion;
    }

    private String formattedPrice() {
        NumberFormat formatter = NumberFormat.getInstance();
        return formatter.format(price) + "원";
    }

    private String formattedQuantity() {
        if (quantity == 0) {
            return "재고 없음";
        }
        return quantity + "개";
    }

    private String formattedPromotion() {
        if (promotion == null) {
            return "";
        }
        return promotion;
    }

    @Override
    public String toString() {
        return String.join(" ", "-", name, formattedPrice(), formattedQuantity(), formattedPromotion());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product product)) {
            return false;
        }
        return price == product.price
                && quantity == product.quantity
                && Objects.equals(name, product.name)
                && Objects.equals(promotion, product.promotion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, quantity, promotion);
    }
}
