package store.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import store.dto.ReceiptDto;
import store.model.OrderItem;
import store.model.Product;
import store.model.ProductType;
import store.model.Receipt;
import store.service.StockService;

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
        Receipt receipt = new Receipt();
        for (OrderItem orderItem : orderItems) {
            Product product = stockService.getProductOrThrow(orderItem.name());
            stockService.validateQuantity(product, orderItem.quantity());
            int promotionStock = product.getQuantityByKey(ProductType.PROMOTION);
            Map<ProductType, Integer> purchaseQuantities
                    = quantityController.getPurchasePromotionQuantity(product, orderItem, promotionStock);
            for (Entry<ProductType, Integer> quantities : purchaseQuantities.entrySet()) {
                if (quantities.getKey() == ProductType.CHANGE) {
                    continue;
                }
                product.purchase(quantities.getKey(), quantities.getValue());
            }
            posController.productScan(receipt, purchaseQuantities, product);
        }
        ReceiptDto receiptDto = posController.issueReceipt(receipt);
        System.out.println(receiptDto.getView());
    }
}
