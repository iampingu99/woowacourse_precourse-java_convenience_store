package store.service;

import java.util.HashMap;
import java.util.Map;
import store.dto.ReceiptDto;
import store.model.Product;
import store.model.ProductType;
import store.model.Promotion;
import store.model.Receipt;

public class PosService {

    public ReceiptDto createReceiptDto(Receipt receipt, String confirm) {
        int totalAmount = receipt.getTotalAmount();
        int promotionDiscount = receipt.getPromotionDiscount();
        int membershipDiscount = 0;
        if (confirm.equals("Y")) {
            membershipDiscount = receipt.getMembershipDiscount();
        }
        int realAmount = totalAmount - promotionDiscount - membershipDiscount;
        return new ReceiptDto(receipt.getPurchaseProducts(), receipt.getFreeProducts(), totalAmount,
                promotionDiscount, membershipDiscount, realAmount);
    }

    public void addPurchaseProduct(Receipt receipt, Map<ProductType, Integer> purchaseQuantity, Product product) {
        Product purchaseProduct = Product.of(product, purchaseQuantity);
        receipt.addPurchaseProducts(purchaseProduct);
    }

    public int getFreeProductCount(Map<ProductType, Integer> purchaseQuantity, Product product) {
        Promotion promotion = product.getPromotion();
        int purchasePromotionQuantity = purchaseQuantity.getOrDefault(ProductType.PROMOTION, 0);
        return promotion.calcFreeProductCount(purchasePromotionQuantity);
    }

    public void addFreeProduct(Receipt receipt, Map<ProductType, Integer> purchaseQuantity, Product product) {
        int freeProductCount = getFreeProductCount(purchaseQuantity, product);
        Map<ProductType, Integer> freeQuantities = new HashMap<>();
        freeQuantities.put(ProductType.PROMOTION, freeProductCount);
        Product freeProduct = Product.of(product, freeQuantities);
        receipt.addFreeProducts(freeProduct);
    }
}
