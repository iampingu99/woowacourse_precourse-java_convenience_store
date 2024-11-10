package store.controller;

import camp.nextstep.edu.missionutils.Console;
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
                String input = InputView.confirmAdditionalPurchase();
                validateConfirm(input);
                return input;
            } catch (IllegalArgumentException exception) {
                OutputView.printError(exception);
            }
        }
    }

    public String confirmApplyPromotion() {
        while (true) {
            try {
                System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
                String confirm = Console.readLine();
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
                System.out.println("현재 " + product.getName() + "은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
                String confirm = Console.readLine();
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
                System.out.println(
                        "현재 " + product.getName() + " " + changeStock + "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
                String confirm = Console.readLine();
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
