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

            validateStock(quantities, item.quantity());

            Integer promotionStock = quantities.getOrDefault(ProductType.PROMOTION, null);
            if (promotionStock == null) { //일반 재고만 존재하는 경우
                product.addQuantity(ProductType.REGULAR, item.quantity());
            } else if (promotionStock > item.quantity()) {//프로모션 재고로 구매 가능한 경우
                promotionPurchase(item, promotion, product, promotionStock);
            } else { //일반 재고 + 프로모션 재고로 구매 가능한 경우
                mixPurchase(item, promotion, promotionStock, product);
            }
        }
    }

    private void promotionPurchase(OrderItem item, Promotion promotion, Product product, Integer promotionStock) {
        int promotionUnit = promotion.getBuy() + promotion.getGet();
        if (item.quantity() % promotionUnit == promotion.getBuy()) {
            String confirm = confirm("현재 " + item.name() + "은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
            if (confirm.equals("Y")) {
                product.purchase(ProductType.PROMOTION, item.quantity() + 1);
            } else {
                product.purchase(ProductType.PROMOTION, item.quantity());
            }
        }
        product.addQuantity(ProductType.PROMOTION, promotionStock);
    }

    private void mixPurchase(OrderItem item, Promotion promotion, Integer promotionStock, Product product) {
        int changeStock = calculateNonPromotionQuantity(promotion, promotionStock, item.quantity());
        String confirm = confirm(
                "현재 " + item.name() + " " + changeStock + "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
        if (confirm.equals("Y")) {
            product.purchase(ProductType.PROMOTION, promotionStock);
            product.purchase(ProductType.REGULAR, item.quantity() - promotionStock);
        } else {
            product.purchase(ProductType.PROMOTION, promotionStock);
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

    private void validateStock(Map<ProductType, Integer> quantities, int purchaseQuantity) {
        int totalCount = quantities.values().stream().mapToInt(Integer::intValue).sum();
        if (totalCount < purchaseQuantity) {
            throw new RuntimeException("재고 수량을 초과하여 구매할 수 없습니다.");
        }
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
