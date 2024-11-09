package store.service;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.model.OrderItem;
import store.model.Product;
import store.model.ProductType;
import store.model.Promotion;
import store.model.Quantity;
import store.model.Stock;
import store.repositroy.StoreRepository;

public class StoreService {
    private final Stock stock;
    private final List<Promotion> promotions;

    public StoreService() {
        StoreRepository storeRepository = new StoreRepository();
        this.stock = new Stock(storeRepository.getProducts());
        this.promotions = storeRepository.getPromotions();
    }

    public Stock getStock() {
        return stock;
    }

    public void run(String input) {
        List<OrderItem> orders = createOrder(input);
        for (OrderItem item : orders) {
            List<Product> products = isExistProduct(item.name());
            int totalCount = products.stream().mapToInt(Product::getQuantity).sum();
            if (totalCount < item.quantity()) {
                throw new RuntimeException("재고 수량을 초과하여 구매할 수 없습니다." + totalCount + " : " + item.quantity());
            }
            purchase(products, item);
        }
    }

    public void purchase(List<Product> products, OrderItem item) {
        Product regularProduct = products.stream()
                .filter(product -> product.getName().equals(item.name()) && product.getPromotion() == null)
                .findFirst().get();
        Product promotionProduct = products.stream()
                .filter(product -> product.getName().equals(item.name()) && product.getPromotion() != null)
                .findFirst().orElse(null);

        //일반 재고만 존재하는 경우
        if (promotionProduct == null) {
            regularProduct.purchase(item.quantity());
            return;
        }

        Map<ProductType, Integer> quantities = new HashMap<>();
        quantities.put(ProductType.REGULAR, 50);
        quantities.put(ProductType.PROMOTION, 20);
        Quantity quantity = new Quantity(quantities);
        quantity.getQuantity(ProductType.PROMOTION);

        //
        Promotion findPromotion = promotions.stream()
                .filter(promotion -> promotion.getName().equals(promotionProduct.getName()))
                .findFirst().get();
        int promotionQuantity = promotionProduct.getQuantity();
        if (promotionQuantity <= item.quantity()) {
            int change = calculateNonPromotionQuantity(findPromotion, promotionQuantity, item.quantity());
            System.out.println("현재 " + item.name() + " " + change + "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
            String input = Console.readLine();
            if (input.equals("N")) {
                promotionProduct.purchase(item.quantity());
                regularProduct.purchase(item.quantity() - promotionQuantity);
            }
        }
    }

    private int calculateNonPromotionQuantity(Promotion promotion, int promotionQuantity, int buy) {
        int promotionUnit = promotion.getBuy() + promotion.getGet();
        return promotionQuantity % promotionUnit + buy - promotionQuantity;
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
