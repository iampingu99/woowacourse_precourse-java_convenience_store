package lab.service;

import java.util.Map;
import lab.model.Product;
import lab.model.ProductType;
import lab.model.Stock;

public class StockService {
    private final Stock stock;

    public StockService(Stock stock) {
        this.stock = stock;
    }

    public Product getProductOrThrow(String productName) {
        return stock.findByProductName(productName)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }

    public Map<ProductType, Integer> getQuantitiesOrThrow(Product product, int purchaseQuantity) {
        int totalCount = product.getQuantities().values().stream()
                .mapToInt(Integer::intValue)
                .sum();
        if (totalCount < purchaseQuantity) {
            throw new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다.");
        }
        return product.getQuantities();
    }
}
