package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;
import store.model.OrderItem;

public class InputView {

    public List<OrderItem> readItem() {
        while (true) {
            System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
            try {
                String input = Console.readLine();
                return createOrder(input);
            } catch (Exception e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
    }

    private List<OrderItem> createOrder(String orders) {
        return Arrays.stream(orders.split("\\[,]"))
                .map(OrderItem::from)
                .toList();
    }
}
