package lab.repository;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import lab.model.Product;
import lab.model.ProductType;
import lab.model.Promotion;
import lab.model.Stock;
import org.junit.jupiter.api.Test;

class StoreRepositoryTest {

    @Test
    void createStock() {
        assertSimpleTest(() -> {
            StoreRepository storeRepository = new StoreRepository();
            Stock stock = new Stock(storeRepository.getPromotions());
            Map<String, Product> products = storeRepository.getProduct(stock).getProducts();
            Product product = products.get("콜라");
            Promotion promotion = product.getPromotion();
            Promotion expectedPromotion = Promotion.from("탄산2+1,2,1,2024-01-01,2024-12-31");

            assertThat(product.getName()).isEqualTo("콜라");
            assertThat(product.getPrice()).isEqualTo(1000);
            assertThat(product.getQuantities()).isEqualTo(
                    Map.of(ProductType.REGULAR, 10, ProductType.PROMOTION, 10));
            assertThat(promotion.getName()).isEqualTo(expectedPromotion.getName());
            assertThat(promotion.getBuy()).isEqualTo(expectedPromotion.getBuy());
            assertThat(promotion.getGet()).isEqualTo(expectedPromotion.getGet());
        });
    }

}
