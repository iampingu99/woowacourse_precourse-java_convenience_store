package store.controller;

import camp.nextstep.edu.missionutils.Console;
import java.util.Map;
import store.model.OrderItem;
import store.model.Product;
import store.model.ProductType;
import store.service.QuantityService;
import store.view.OutputView;

public class QuantityController {

    QuantityService quantityService = new QuantityService();

    public Map<ProductType, Integer> getPurchasePromotionQuantity(Product product, OrderItem orderItem,
                                                                  int promotionStock) {
        if (promotionStock == 0) {
            return quantityService.regularPurchase(orderItem);
        }
        if (promotionStock > orderItem.quantity()) {
            if (product.getPromotion().qualifiesForFreeProduct(promotionStock)) {
                String confirm = confirmAddFreeProduct(product);
                return quantityService.promotionPurchase(orderItem, confirm);
            }
            return quantityService.promotionPurchase(orderItem, "N");
        }
        int changeStock = quantityService.calculateNonPromotionQuantity(product.getPromotion(), promotionStock,
                orderItem.quantity());
        String confirm = getNonPromotionConfirm(product, changeStock);
        return quantityService.mixPurchase(orderItem, promotionStock, confirm, changeStock);
    }

    public String confirmAddFreeProduct(Product product) {
        while (true) {
            try {
                System.out.println("현재 " + product.getName() + "은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
                return Console.readLine();
            } catch (IllegalArgumentException exception) {
                OutputView.printError(exception);
            }
        }
    }

    public String getNonPromotionConfirm(Product product, int changeStock) {
        while (true) {
            try {
                System.out.println(
                        "현재 " + product.getName() + " " + changeStock + "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
                return Console.readLine();
            } catch (IllegalArgumentException exception) {
                OutputView.printError(exception);
            }
        }
    }
}
