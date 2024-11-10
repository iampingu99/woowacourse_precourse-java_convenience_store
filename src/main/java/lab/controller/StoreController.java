package lab.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lab.dto.ReceiptDto;
import lab.model.OrderItem;
import lab.model.Product;
import lab.model.ProductType;
import lab.service.StockService;

public class StoreController {
    private final PosController posController;
    private final QuantityController quantityController;
    private final StockService stockService;

    public StoreController(PosController posController, QuantityController quantityController,
                           StockService stockService) {
        this.posController = posController;
        this.quantityController = quantityController;
        this.stockService = stockService;
    }

    public void run(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            Product product = stockService.getProductOrThrow(orderItem.name());
            stockService.validateQuantity(product, orderItem.quantity());
            int promotionStock = product.getQuantityByKey(ProductType.PROMOTION);
            Map<ProductType, Integer> purchaseQuantities
                    = quantityController.getPurchasePromotionQuantity(product, orderItem, promotionStock);
            for (Entry<ProductType, Integer> quantities : purchaseQuantities.entrySet()) {
                product.purchase(quantities.getKey(), quantities.getValue());
            }
            posController.productScan(purchaseQuantities, product);
        }
        ReceiptDto receiptDto = posController.issueReceipt();
        System.out.println(receiptDto.getView());
    }
}
