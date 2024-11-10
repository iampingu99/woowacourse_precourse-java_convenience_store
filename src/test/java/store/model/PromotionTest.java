package store.model;

import static camp.nextstep.edu.missionutils.test.Assertions.assertNowTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import lab.model.Promotion;
import org.junit.jupiter.api.Test;

class PromotionTest {

    @Test
    void 프로모션_적용_기간_참() {
        assertNowTest(() -> {
            lab.model.Promotion promotion = lab.model.Promotion.from("탄산2+1,2,1,2024-01-01,2024-12-31");
            assertThat(promotion.isAvailable(DateTimes.now())).isEqualTo(true);
        }, LocalDate.of(2024, 2, 1).atStartOfDay());
    }

    @Test
    void 프로모션_적용_기간_거짓() {
        assertNowTest(() -> {
            lab.model.Promotion promotion = lab.model.Promotion.from("반짝할인,1,1,2024-11-01,2024-11-30");
            assertThat(promotion.isAvailable(DateTimes.now())).isEqualTo(false);
        }, LocalDate.of(2024, 2, 1).atStartOfDay());
    }

    @Test
    void 프로모션_증정_상품_개수_계산() {
        assertNowTest(() -> {
            lab.model.Promotion promotion = Promotion.from("탄산2+1,2,1,2024-01-01,2024-12-31");
            assertThat(promotion.calcFreeProductCount(3)).isEqualTo(1);
            assertThat(promotion.calcFreeProductCount(5)).isEqualTo(1);
            assertThat(promotion.calcFreeProductCount(10)).isEqualTo(3);
        }, LocalDate.of(2024, 2, 1).atStartOfDay());
    }
}
