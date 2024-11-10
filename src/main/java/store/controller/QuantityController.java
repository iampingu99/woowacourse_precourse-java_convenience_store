package store.controller;

import java.util.Map;
import store.model.OrderItem;
import store.model.Product;
import store.model.ProductType;
import store.service.QuantityService;

public class QuantityController {

    InputController inputController;
    QuantityService quantityService;

    public QuantityController(InputController inputController, QuantityService quantityService) {
        this.inputController = inputController;
        this.quantityService = quantityService;
    }

    public Map<ProductType, Integer> getPurchasePromotionQuantity(Product product, OrderItem orderItem,
                                                                  int promotionStock) {
        if (promotionStock == 0) {
            return quantityService.regularPurchase(orderItem);
        }
        if (promotionStock > orderItem.quantity()) {
            if (product.getPromotion().qualifiesForFreeProduct(orderItem.quantity())) {
                String confirm = inputController.confirmAddFreeProduct(product);
                return quantityService.promotionPurchase(product, orderItem, confirm);
            }
            return quantityService.promotionPurchase(product, orderItem, "N");
        }
        int changeStock = quantityService.calculateNonPromotionQuantity(product.getPromotion(), promotionStock,
                orderItem.quantity());
        String confirm = inputController.getNonPromotionConfirm(product, changeStock);
        return quantityService.mixPurchase(orderItem, promotionStock, confirm, changeStock);
    }
}
