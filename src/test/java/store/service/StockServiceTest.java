package store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import camp.nextstep.edu.missionutils.test.Assertions;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.model.Product;
import store.model.ProductType;
import store.model.Promotion;
import store.model.Stock;

class StockServiceTest {

    private StockService stockService;

    @BeforeEach
    void setUp() {
        Map<String, Product> products = new HashMap<>();
        Promotion promotion = new Promotion("탄산2+1", 2, 1, LocalDate.parse("2024-01-01"),
                LocalDate.parse("2024-12-31"));
        Product product = new Product("콜라", 1000);
        product.addQuantity(ProductType.REGULAR, 10);
        product.addQuantity(ProductType.PROMOTION, 10);
        product.setPromotion(promotion);
        products.put(product.getName(), product);

        stockService = new StockService(new Stock(products));
    }

    @Test
    void 상품_이름_검색() {
        Assertions.assertSimpleTest(() -> {
            Product findProduct = stockService.getProductOrThrow("콜라");

            assertThat(findProduct.getName()).isEqualTo("콜라");
            assertThat(findProduct.getPrice()).isEqualTo(1000);
            assertThat(findProduct.getQuantities().get(ProductType.REGULAR)).isEqualTo(10);
            assertThat(findProduct.getQuantities().get(ProductType.PROMOTION)).isEqualTo(10);

            assertThat(findProduct.getPromotion().getName()).isEqualTo("탄산2+1");
            assertThat(findProduct.getPromotion().getBuy()).isEqualTo(2);
            assertThat(findProduct.getPromotion().getGet()).isEqualTo(1);
        });
    }

    @Test
    void 존재하지_않는_상품_이름인_경우_예외가_발생한다() {
        assertThatThrownBy(() -> stockService.getProductOrThrow("사이다"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 재고_수량을_초과해서_구매하려는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> {
            Product foundProduct = stockService.getProductOrThrow("콜라");
            stockService.validateQuantity(foundProduct, 21);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
