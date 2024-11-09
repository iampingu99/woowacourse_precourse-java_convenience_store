package lab.controller;

import java.util.Arrays;
import java.util.List;
import lab.model.OrderItem;
import lab.view.InputView;
import lab.view.OutputView;

public class InputController {

    public List<OrderItem> getPurchaseProduct(InputView inputView, OutputView outputView) {
        while (true) {
            try {
                return createOrder(inputView.readItem());
            } catch (IllegalArgumentException exception) {
                outputView.printError(exception);
            }
        }
    }

    private List<OrderItem> createOrder(String orders) {
        return Arrays.stream(orders.split(","))
                .map(OrderItem::from)
                .toList();
    }
}
