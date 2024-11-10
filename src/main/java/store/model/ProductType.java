package store.model;

public enum ProductType {
    REGULAR, PROMOTION;

    public static ProductType from(String productType) {
        if (productType.equals("null")) {
            return REGULAR;
        }
        return PROMOTION;
    }
}
