package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;
import store.model.OrderItem;
import store.model.Product;
import store.model.Stock;
import store.repositroy.StoreRepository;

public class InputView {
    private final Stock stock;

    public InputView() {
        this.stock = new Stock(new StoreRepository().getProducts());
    }

    public void readItem() {
        while (true) {
            System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
            try {
                String input = Console.readLine();
                List<OrderItem> order = createOrder(input);
                for (OrderItem orderItem : order) {
                    isExistProduct(orderItem.name());
                }
                System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
                String exitInput = Console.readLine();
                if (exitInput.equals("N")) {
                    return;
                }
            } catch (Exception e) {
                System.out.println("[ERROR] " + e.getMessage() + "다시 입력 해주세요.");
            }
        }
    }

    public List<Product> isExistProduct(String productName) {
        List<Product> product = stock.getProductByName(productName);
        if (product.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
        return product;
    }

    private List<OrderItem> createOrder(String orders) {
        return Arrays.stream(orders.split("\\[,]"))
                .map(OrderItem::from)
                .toList();
    }
}
