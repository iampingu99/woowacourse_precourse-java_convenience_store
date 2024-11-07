package store.model;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderItemTest {

    @Test
    @DisplayName("구매 상품 생성")
    void create_order() {
        assertSimpleTest(() -> {
            OrderItem item = OrderItem.from("[사이다-2]");
            assertThat(item.name()).isEqualTo(new OrderItem("사이다", 2).name());
            assertThat(item.quantity()).isEqualTo(new OrderItem("사이다", 2).quantity());
        });
    }
}
