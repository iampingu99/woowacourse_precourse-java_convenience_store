package store.repositroy;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import camp.nextstep.edu.missionutils.test.Assertions;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.Promotion;

class StoreRepositoryTest {

    @Test
    @DisplayName("프로모션 목록 생성")
    void loadPromotions() {
        Assertions.assertSimpleTest(() -> {
            StoreRepository storeRepository = new StoreRepository();
            List<Promotion> promotions = storeRepository.getPromotions();
            assertThat(promotions).isEqualTo(
                    List.of(
                            new Promotion("탄산2+1", 2, 1, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31)),
                            new Promotion("MD추천상품", 1, 1, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31)),
                            new Promotion("반짝할인", 1, 1, LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 30))
                    )
            );
        });
    }
}
