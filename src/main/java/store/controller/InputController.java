package store.controller;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import store.model.OrderItem;
import store.model.Product;
import store.view.InputView;
import store.view.OutputView;

public class InputController {
    private static final Pattern CONFIRM_PATTERN = Pattern.compile("[N|Y]");

    public List<OrderItem> getPurchaseProduct() {
        while (true) {
            try {
                return createOrder(InputView.readItem());
            } catch (IllegalArgumentException exception) {
                OutputView.printError(exception);
            }
        }
    }

    public String confirmAdditionalPurchase() {
        while (true) {
            try {
                String confirm = InputView.confirmAdditionalPurchase();
                validateConfirm(confirm);
                return confirm;
            } catch (IllegalArgumentException exception) {
                OutputView.printError(exception);
            }
        }
    }

    public String confirmApplyPromotion() {
        while (true) {
            try {
                String confirm = InputView.confirmApplyPromotion();
                validateConfirm(confirm);
                return confirm;
            } catch (IllegalArgumentException exception) {
                OutputView.printError(exception);
            }
        }
    }

    public String confirmAddFreeProduct(Product product) {
        while (true) {
            try {
                String confirm = InputView.confirmAddFreeProduct(product);
                validateConfirm(confirm);
                return confirm;
            } catch (IllegalArgumentException exception) {
                OutputView.printError(exception);
            }
        }
    }

    public String getNonPromotionConfirm(Product product, int changeStock) {
        while (true) {
            try {
                String confirm = InputView.confirmPromotionConfirm(product, changeStock);
                validateConfirm(confirm);
                return confirm;
            } catch (IllegalArgumentException exception) {
                OutputView.printError(exception);
            }
        }
    }

    private void validateConfirm(String input) {
        if (!CONFIRM_PATTERN.matcher(input).matches()) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }

    private List<OrderItem> createOrder(String orders) {
        return Arrays.stream(orders.split(","))
                .map(OrderItem::from)
                .toList();
    }
}
