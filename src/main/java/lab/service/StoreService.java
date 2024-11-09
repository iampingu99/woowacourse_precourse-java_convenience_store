package lab.service;


import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lab.model.OrderItem;
import lab.model.Product;
import lab.model.ProductType;
import lab.model.Promotion;
import lab.model.Receipt;
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
        Receipt receipt = new Receipt();
        for (OrderItem item : orders) {
            String purchaseName = item.name();
            int purchaseAmount = item.quantity();

            Product product = findProduct(purchaseName);
            Map<ProductType, Integer> quantities = product.getQuantities();
            Promotion promotion = product.getPromotion();

            validateStock(quantities, purchaseAmount);

            int promotionStock = quantities.getOrDefault(ProductType.PROMOTION, 0);
            int purchasePromotionQuantity = getPurchasePromotionQuantity(item, promotionStock, product, receipt,
                    promotion);
            if (purchasePromotionQuantity > 0) {
                receipt.addFreeItem(product, promotion, purchasePromotionQuantity);
            }
        }
        System.out.println(receipt);
    }

    private int getPurchasePromotionQuantity(OrderItem item, int promotionStock, Product product, Receipt receipt,
                                             Promotion promotion) {
        //일반 재고로만 구매 가능한 경우
        if (promotionStock == 0) {
            return regularPurchase(product, item, receipt);
        }
        //프로모션 재고로 구매 가능한 경우
        if (promotionStock > item.quantity()) {
            return promotionPurchase(receipt, item, promotion, product);
        }
        //일반 재고 + 프로모션 재고로 구매 가능한 경우
        return mixPurchase(receipt, item, promotion, promotionStock, product);
    }

    private int regularPurchase(Product product, OrderItem item, Receipt receipt) {
        product.addQuantity(ProductType.REGULAR, item.quantity()); //일반 재고만 존재하는 경우
        receipt.purchase(product, ProductType.REGULAR, item.quantity());
        return 0;
    }

    private int promotionPurchase(Receipt receipt, OrderItem item, Promotion promotion, Product product) {
        int promotionUnit = promotion.getBuy() + promotion.getGet();
        if (item.quantity() % promotionUnit == promotion.getBuy()) {
            String confirm = confirm("현재 " + item.name() + "은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
            if (confirm.equals("Y")) {
                receipt.purchase(product, ProductType.PROMOTION, item.quantity() + 1);
                return item.quantity() + 1;
            }
        }
        receipt.purchase(product, ProductType.PROMOTION, item.quantity());
        return item.quantity();
    }

    private int mixPurchase(Receipt receipt, OrderItem item, Promotion promotion, Integer promotionStock,
                            Product product) {
        int changeStock = calculateNonPromotionQuantity(promotion, promotionStock, item.quantity());
        String confirm = confirm(
                "현재 " + item.name() + " " + changeStock + "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
        if (confirm.equals("Y")) {
            receipt.purchase(product, ProductType.PROMOTION, promotionStock);
            receipt.purchase(product, ProductType.REGULAR, item.quantity() - promotionStock);
            return promotionStock - changeStock;
        }
        receipt.purchase(product, ProductType.PROMOTION, item.quantity() - changeStock);
        return promotionStock;
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
        return Arrays.stream(orders.split(","))
                .map(OrderItem::from)
                .toList();
    }
}
