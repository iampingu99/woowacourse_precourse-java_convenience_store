package lab.model;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Product {
    private final String name;
    private final int price;
    private final Map<ProductType, Integer> quantities = new HashMap<>();
    private Promotion promotion;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public static Product of(ProductRecord record, Map<String, Promotion> promotions) {
        Product product = new Product(record.name(), record.price());
        ProductType productType = ProductType.from(record.promotion());
        product.addQuantity(productType, record.quantity());

        Optional<Promotion> promotion = Optional.ofNullable(promotions.get(record.promotion()));
        promotion.ifPresent(product::setPromotion);

        return product;
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

    public void purchase(ProductType productType, int quantity) {
        quantities.put(productType, quantities.get(productType) - quantity);
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    private String formattedPrice() {
        NumberFormat formatter = NumberFormat.getInstance();
        return formatter.format(price) + "원";
    }

    private String formattedQuantity(int quantity) {
        if (quantity == 0) {
            return "재고 없음";
        }
        return quantity + "개";
    }

    private String formattedPromotion(ProductType productType) {
        if (productType == ProductType.REGULAR) {
            return "";
        }
        return promotion.getName();
    }

    @Override
    public String toString() {
        return quantities.entrySet().stream()
                .map(entry ->
                        String.join(" ", "-", name, formattedPrice(), formattedQuantity(entry.getValue()),
                                formattedPromotion(entry.getKey()))
                ).collect(Collectors.joining("\n"));
    }
}
