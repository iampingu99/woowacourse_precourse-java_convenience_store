package lab.model;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Stock {
    private final Map<String, Promotion> promotions;
    private final Map<String, Product> products = new HashMap<>();

    public Stock(Map<String, Promotion> promotions) {
        this.promotions = promotions;
    }

    public Map<String, Product> getProducts() {
        return products;
    }

    public void addProduct(String record) {
        ProductRecord productRecord = ProductRecord.from(record);
        ProductType productType = ProductType.from(productRecord.promotion());
        Promotion promotion = promotions.getOrDefault(productRecord.promotion(), null);
        Product product = products.get(productRecord.name());

        if (product == null) {
            product = new Product(productRecord.name(), productRecord.price());
            products.put(productRecord.name(), product);
        }
        product.addQuantity(productType, productRecord.quantity());
        if (promotion != null) {
            product.setPromotion(promotion);
        }
    }

    @Override
    public String toString() {
        return products.values().stream()
                .map(Product::toString)
                .collect(Collectors.joining("\n"));
    }
}

