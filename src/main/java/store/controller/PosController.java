package store.controller;

import camp.nextstep.edu.missionutils.Console;
import java.util.Map;
import store.dto.ReceiptDto;
import store.model.Product;
import store.model.ProductType;
import store.service.PosService;
import store.view.OutputView;

public class PosController {

    private final PosService posService;

    public PosController(PosService posService) {
        this.posService = posService;
    }

    public ReceiptDto issueReceipt() {
        String confirm = confirmApplyPromotion();
        return posService.createReceiptDto(confirm);
    }

    public void productScan(Map<ProductType, Integer> purchaseQuantity, Product product) {
        posService.addPurchaseProduct(purchaseQuantity, product);
        if (purchaseQuantity.getOrDefault(ProductType.PROMOTION, 0) > 0) {
            posService.addFreeProduct(purchaseQuantity, product);
        }
    }

    public String confirmApplyPromotion() {
        while (true) {
            try {
                System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
                return Console.readLine();
            } catch (IllegalArgumentException exception) {
                OutputView.printError(exception);
            }
        }
    }
}
