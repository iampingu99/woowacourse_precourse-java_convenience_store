package lab.model;

public record PurchaseItem(
        ProductType productType,
        String name,
        int price,
        int quantity
) {
    public static PurchaseItem of(ProductType productType, String name, int price, int quantity) {
        return new PurchaseItem(productType, name, price, quantity);
    }
}
