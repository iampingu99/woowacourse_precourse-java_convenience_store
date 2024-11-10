package lab.service;

import java.util.HashMap;
import java.util.Map;
import lab.model.OrderItem;
import lab.model.ProductType;
import lab.model.Promotion;

public class QuantityService {

    public Map<ProductType, Integer> regularPurchase(OrderItem item) {
        Map<ProductType, Integer> purchaseQuantity = new HashMap<>();
        purchaseQuantity.put(ProductType.REGULAR, item.quantity());
        return purchaseQuantity;
    }

    public Map<ProductType, Integer> promotionPurchase(OrderItem item, String confirm) {
        Map<ProductType, Integer> purchaseQuantity = new HashMap<>();
        if (confirm.equals("Y")) {
            purchaseQuantity.put(ProductType.PROMOTION, item.quantity() + 1);
            return purchaseQuantity;
        }
        purchaseQuantity.put(ProductType.PROMOTION, item.quantity());
        return purchaseQuantity;
    }

    public Map<ProductType, Integer> mixPurchase(OrderItem item, int promotionStock, String confirm, int changeStock) {
        Map<ProductType, Integer> purchaseQuantity = new HashMap<>();
        if (confirm.equals("Y")) {
            purchaseQuantity.put(ProductType.PROMOTION, promotionStock);
            purchaseQuantity.put(ProductType.REGULAR, item.quantity() - promotionStock);
            return purchaseQuantity;
        }
        purchaseQuantity.put(ProductType.PROMOTION, promotionStock - changeStock);
        return purchaseQuantity;
    }

    public int calculateNonPromotionQuantity(Promotion promotion, int promotionQuantity, int buy) {
        int promotionUnit = promotion.getBuy() + promotion.getGet();
        return promotionQuantity % promotionUnit + buy - promotionQuantity;
    }
}
