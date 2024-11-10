package store.controller;

import java.util.Map;
import store.dto.ReceiptDto;
import store.model.Product;
import store.model.ProductType;
import store.service.PosService;

public class PosController {

    private final InputController inputController;
    private final PosService posService;

    public PosController(InputController inputController, PosService posService) {
        this.inputController = inputController;
        this.posService = posService;
    }

    public ReceiptDto issueReceipt() {
        String confirm = inputController.confirmApplyPromotion();
        return posService.createReceiptDto(confirm);
    }

    public void productScan(Map<ProductType, Integer> purchaseQuantity, Product product) {
        posService.addPurchaseProduct(purchaseQuantity, product);
        if (purchaseQuantity.getOrDefault(ProductType.PROMOTION, 0) > 0) {
            posService.addFreeProduct(purchaseQuantity, product);
        }
    }
}
