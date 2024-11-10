package lab.model;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Stock {
    private final Map<String, Product> products;

    public Stock(Map<String, Product> products) {
        this.products = products;
    }

    public Map<String, Product> getProducts() {
        return products;
    }
    
    public Optional<Product> findByProductName(String productName) {
        return Optional.ofNullable(products.get(productName));
    }

    @Override
    public String toString() {
        return products.values().stream()
                .map(Product::toString)
                .collect(Collectors.joining("\n"));
    }
}

