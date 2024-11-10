package lab.service;

import java.util.Map;
import lab.model.Product;
import lab.model.ProductType;
import lab.model.Receipts;

public class PosService {

    private final Receipts receipts;

    public PosService(Receipts receipts) {
        this.receipts = receipts;
    }

    public void addPurchaseProduct(Map<ProductType, Integer> purchaseQuantity, Product product) {
        Product purchaseProduct = Product.of(product, purchaseQuantity);
        receipts.addPurchaseProducts(purchaseProduct);
    }
}
