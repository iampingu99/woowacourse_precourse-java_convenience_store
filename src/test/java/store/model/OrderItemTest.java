package store.model;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    @DisplayName("구매할 상품의 수량이 0이거나 숫자가 아닌 경우 예외가 발생한다")
    void product_quantity_not_number() {
        assertThatThrownBy(() -> OrderItem.from("[사이다-a]"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> OrderItem.from("[사이다-0]"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
