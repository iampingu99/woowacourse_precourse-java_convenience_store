package lab.service;

import java.util.HashMap;
import java.util.Map;
import lab.dto.ReceiptDto;
import lab.model.Product;
import lab.model.ProductType;
import lab.model.Promotion;
import lab.model.Receipt;

public class PosService {

    private final Receipt receipt;

    public PosService(Receipt receipt) {
        this.receipt = receipt;
    }

    public ReceiptDto createReceiptDto(String confirm) {
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

    public void addPurchaseProduct(Map<ProductType, Integer> purchaseQuantity, Product product) {
        Product purchaseProduct = Product.of(product, purchaseQuantity);
        receipt.addPurchaseProducts(purchaseProduct);
    }

    public int getFreeProductCount(Map<ProductType, Integer> purchaseQuantity, Product product) {
        Promotion promotion = product.getPromotion();
        int purchasePromotionQuantity = purchaseQuantity.getOrDefault(ProductType.PROMOTION, 0);
        return promotion.calcFreeProductCount(purchasePromotionQuantity);
    }

    public void addFreeProduct(Map<ProductType, Integer> purchaseQuantity, Product product) {
        int freeProductCount = getFreeProductCount(purchaseQuantity, product);
        Map<ProductType, Integer> freeQuantities = new HashMap<>();
        freeQuantities.put(ProductType.PROMOTION, freeProductCount);
        Product freeProduct = Product.of(product, freeQuantities);
        receipt.addFreeProducts(freeProduct);
    }
}
