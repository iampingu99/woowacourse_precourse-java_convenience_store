package store.model;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    @DisplayName("상품 출력")
    void print_product() {
        assertSimpleTest(() -> {
            Product promotion = Product.from("콜라,1000,10,탄산2+1");
            Product regular = Product.from("콜라,1000,10,null");
            assertThat(promotion.toString()).contains("- 콜라 1,000원 10개 탄산2+1");
            assertThat(regular.toString()).contains("- 콜라 1,000원 10개");
        });
    }
}
