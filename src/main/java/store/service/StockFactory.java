package store.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import store.dto.ProductDto;
import store.model.Product;
import store.model.Promotion;
import store.model.Stock;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

public class StockFactory {

    private final List<ProductDto> productDtos;
    private final Map<String, Promotion> promotions;

    public StockFactory(ProductRepository productRepository, PromotionRepository promotionRepository) {
        this.productDtos = productRepository.loadProducts();
        this.promotions = promotionRepository.loadPromotions();
    }

    public Stock createStock() {
        Map<String, Product> products = productDtos.stream().collect(Collectors.toMap(
                ProductDto::name,
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
