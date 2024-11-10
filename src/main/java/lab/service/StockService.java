package lab.service;

import lab.model.Product;
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
}
