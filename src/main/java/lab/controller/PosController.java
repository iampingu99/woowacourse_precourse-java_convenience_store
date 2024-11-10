package lab.controller;

import camp.nextstep.edu.missionutils.Console;
import java.util.Map;
import lab.dto.ReceiptDto;
import lab.model.Product;
import lab.model.ProductType;
import lab.service.PosService;
import lab.view.OutputView;

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
