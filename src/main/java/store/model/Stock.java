package store.model;

import java.util.List;
import java.util.stream.Collectors;

public class Stock {
    private final List<Product> products;

    public Stock(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return products.stream()
                .map(Product::toString)
                .collect(Collectors.joining("\n"));
    }
}
