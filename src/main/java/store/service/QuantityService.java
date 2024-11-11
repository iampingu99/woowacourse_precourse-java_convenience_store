package store.service;

import java.util.HashMap;
import java.util.Map;
import store.model.OrderItem;
import store.model.Product;
import store.model.ProductType;
import store.model.Promotion;

public class QuantityService {

    public Map<ProductType, Integer> regularPurchase(OrderItem item) {
        Map<ProductType, Integer> purchaseQuantity = new HashMap<>();
        purchaseQuantity.put(ProductType.REGULAR, item.quantity());
        return purchaseQuantity;
    }

    public Map<ProductType, Integer> promotionPurchase(Product product, OrderItem item, String confirm) {
        Map<ProductType, Integer> purchaseQuantity = new HashMap<>();
        if (confirm.equals("Y")) {
            purchaseQuantity.put(ProductType.PROMOTION, item.quantity() + 1);
            return purchaseQuantity;
        }
        purchaseQuantity.put(ProductType.PROMOTION, item.quantity());
        int changeStock = calculateNonPromotionQuantity(product.getPromotion(),
                item.quantity(), item.quantity());
        purchaseQuantity.put(ProductType.CHANGE, changeStock);
        return purchaseQuantity;
    }

    public Map<ProductType, Integer> mixPurchase(OrderItem item, int promotionStock, String confirm, int changeStock) {
        Map<ProductType, Integer> purchaseQuantity = new HashMap<>();
        if (confirm.equals("Y")) {
            purchaseQuantity.put(ProductType.PROMOTION, promotionStock);
            purchaseQuantity.put(ProductType.CHANGE, changeStock);
            purchaseQuantity.put(ProductType.REGULAR, item.quantity() - promotionStock);
            return purchaseQuantity;
        }
        purchaseQuantity.put(ProductType.PROMOTION, item.quantity() - changeStock);
        return purchaseQuantity;
    }

    public int calculateNonPromotionQuantity(Promotion promotion, int promotionQuantity, int buy) {
        int promotionUnit = promotion.getBuy() + promotion.getGet();
        return promotionQuantity % promotionUnit + buy - promotionQuantity;
    }
}
