package store.dto;

import java.text.NumberFormat;
import java.util.List;
import store.model.Product;

public record ReceiptDto(
        List<Product> purchaseProducts,
        List<Product> freeProducts,
        int totalAmount,
        int promotionDiscount,
        int membershipDiscount,
        int realAmount
) {
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance();

    public String purchaseProductsView() {
        StringBuilder sb = new StringBuilder();
        for (Product product : purchaseProducts) {
            sb.append(String.format("%s\t\t%d \t%s\n", product.getName(), product.getTotalQuantity(),
                    NUMBER_FORMAT.format(product.getTotalQuantity() * product.getPrice())));
        }
        return sb.toString();
    }

    public String freeProductsView() {
        StringBuilder sb = new StringBuilder();
        for (Product product : freeProducts) {
            sb.append(String.format("%s\t\t%d\n", product.getName(), product.getTotalQuantity()));
        }
        return sb.toString();
    }

    public String totalAmountView() {
        return String.format("%s\t\t%d\t%s", "총구매액",
                purchaseProducts.stream().mapToInt(Product::getTotalQuantity).sum(), NUMBER_FORMAT.format(totalAmount));
    }


    public String promotionDiscountView() {
        return String.format("%s\t\t\t-%s", "행사할인", NUMBER_FORMAT.format(promotionDiscount));
    }

    public String membershipDiscountView() {
        return String.format("%s\t\t\t-%s", "멤버십할인", NUMBER_FORMAT.format(membershipDiscount));
    }

    public String realAmountView() {
        return String.format("%s\t\t\t %s", "내실돈", NUMBER_FORMAT.format(realAmount));
    }

    public String getView() {
        return String.join("\n",
                "==============W 편의점================",
                "상품명\t\t수량\t금액",
                purchaseProductsView(),
                "=============증\t정===============",
                freeProductsView(),
                "====================================",
                totalAmountView(),
                promotionDiscountView(),
                membershipDiscountView(),
                realAmountView());
    }
}
