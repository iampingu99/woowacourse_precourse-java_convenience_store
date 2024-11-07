package store.service;

import java.util.Arrays;
import java.util.List;
import store.model.OrderItem;
import store.model.Product;
import store.model.Stock;
import store.repositroy.StoreRepository;

public class StoreService {
    private final Stock stock;

    public StoreService() {
        this.stock = new Stock(new StoreRepository().getProducts());
    }

    public void run(String input) {
        List<OrderItem> orders = createOrder(input);
        for (OrderItem item : orders) {
            List<Product> products = isExistProduct(item.name());
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
