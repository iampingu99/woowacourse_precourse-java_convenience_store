package store.model;

import java.util.Map;

public class Quantity {
    private final Map<ProductType, Integer> quantities;

    public Quantity(Map<ProductType, Integer> quantities) {
        this.quantities = quantities;
    }

    public int getQuantity(ProductType type) {
        return quantities.getOrDefault(type, 0);
    }
}
