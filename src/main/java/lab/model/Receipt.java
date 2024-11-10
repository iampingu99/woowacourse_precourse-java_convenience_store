package lab.model;

import java.util.ArrayList;
import java.util.List;

public class Receipt {
    List<Product> purchaseProducts = new ArrayList<>();
    List<Product> freeProducts = new ArrayList<>();

    public void addPurchaseProducts(Product product) {
        purchaseProducts.add(product);
    }

    public void addFreeProducts(Product product) {
        freeProducts.add(product);
    }

    public int getTotalAmount() {
        return purchaseProducts.stream().mapToInt(product -> product.getTotalQuantity() * product.getPrice()).sum();
    }

    public int getPromotionDiscount() {
        return freeProducts.stream().mapToInt(product -> product.getTotalQuantity() * product.getPrice()).sum();
    }

    public int getMembershipDiscount() {
        int sum = purchaseProducts.stream()
                .mapToInt(product -> product.getPrice() * product.getQuantityByKey(ProductType.REGULAR))
                .sum();
        return Math.min(sum / 100 * 30, 8000);
    }

    public List<Product> getPurchaseProducts() {
        return purchaseProducts;
    }

    public List<Product> getFreeProducts() {
        return freeProducts;
    }
}
