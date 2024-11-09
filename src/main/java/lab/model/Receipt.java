package lab.model;

import java.util.ArrayList;
import java.util.List;

public class Receipt {
    private final List<PurchaseItem> orderItems = new ArrayList<>();
    private final List<PurchaseItem> freeItems = new ArrayList<>();
    int totalAmount = 0;
    int promotionDiscount = 0;
    int membershipDiscount = 0;
    int realAmount = 0;

    public void purchase(Product product, ProductType productType, int quantity) {
        product.purchase(productType, quantity);
        PurchaseItem purchaseItem = PurchaseItem.of(productType, product.getName(), product.getPrice(), quantity);
        orderItems.add(purchaseItem);
    }

    public void addFreeItem(Product product, Promotion promotion, int purchasePromotionQuantity) {
        int freeProductQuantity = purchasePromotionQuantity / (promotion.getBuy() + promotion.getGet());
        PurchaseItem purchaseItem = PurchaseItem.of(ProductType.PROMOTION, product.getName(), product.getPrice(),
                freeProductQuantity);
        freeItems.add(purchaseItem);
    }

    public void calcTotalAmount() {
        totalAmount = orderItems.stream().mapToInt(o -> o.price() * o.quantity()).sum();
    }
}
