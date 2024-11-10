package lab.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lab.model.Product;
import lab.model.ProductRecord;
import lab.model.Promotion;
import lab.model.Stock;
import lab.repository.ProductRepository;
import lab.repository.PromotionRepository;

public class StockFactory {

    private final List<ProductRecord> productRecords;
    private final Map<String, Promotion> promotions;

    public StockFactory(ProductRepository productRepository, PromotionRepository promotionRepository) {
        this.productRecords = productRepository.loadProducts();
        this.promotions = promotionRepository.loadPromotions();
    }

    public Stock createStock() {
        Map<String, Product> products = productRecords.stream().collect(Collectors.toMap(
                ProductRecord::name,
                productRecord -> Product.of(productRecord, promotions),
                this::mergeProducts
        ));
        return new Stock(products);
    }

    private Product mergeProducts(Product exsiting, Product replacment) {
        replacment.getQuantities().forEach(exsiting::addQuantity);
        return exsiting;
    }
}
