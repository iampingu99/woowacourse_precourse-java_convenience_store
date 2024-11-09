package lab.model;

import java.util.HashMap;
import java.util.Map;

public class Product {
    private final String name;
    private final int price;
    private final Map<ProductType, Integer> quantities = new HashMap<>();
    private Promotion promotion;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public Map<ProductType, Integer> getQuantities() {
        return quantities;
    }

    public void addQuantity(ProductType productType, int quantity) {
        quantities.put(productType, quantity);
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }
}
