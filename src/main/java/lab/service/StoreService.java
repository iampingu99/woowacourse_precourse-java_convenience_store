package lab.service;


import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lab.model.OrderItem;
import lab.model.Product;
import lab.model.ProductType;
import lab.model.Promotion;
import lab.model.Stock;
import lab.repository.StoreRepository;

public class StoreService {
    private final Stock stock;

    public StoreService() {
        StoreRepository storeRepository = new StoreRepository();
        Stock stock = new Stock(storeRepository.getPromotions());
        this.stock = storeRepository.getProduct(stock);
    }

    public Stock getStock() {
        return stock;
    }

    public void run(String input) {
        List<OrderItem> orders = createOrder(input);
        for (OrderItem item : orders) {
            Product product = findProduct(item.name());
            Map<ProductType, Integer> quantities = product.getQuantities();
            Promotion promotion = product.getPromotion();

            int totalStock = findStock(product, item.quantity());
            Integer promotionStock = quantities.getOrDefault(ProductType.PROMOTION, null);
            if (promotionStock == null) { //일반 재고만 존재하는 경우
                product.addQuantity(ProductType.REGULAR, item.quantity());
            }
        }
    }

    public String confirm(String str) {
        System.out.println(str);
        return Console.readLine();
    }

    private int calculateNonPromotionQuantity(Promotion promotion, int promotionQuantity, int buy) {
        int promotionUnit = promotion.getBuy() + promotion.getGet();
        return promotionQuantity % promotionUnit + buy - promotionQuantity;
    }

    public int findStock(Product product, int purchaseQuantity) {
        Map<ProductType, Integer> quantities = product.getQuantities();
        int totalCount = quantities.values().stream().mapToInt(Integer::intValue).sum();
        if (totalCount < purchaseQuantity) {
            throw new RuntimeException("재고 수량을 초과하여 구매할 수 없습니다." + totalCount + " : " + purchaseQuantity);
        }
        return totalCount;
    }

    public Product findProduct(String productName) {
        Product product = stock.getProducts().getOrDefault(productName, null);
        if (product == null) {
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
