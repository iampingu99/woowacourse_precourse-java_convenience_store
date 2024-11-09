package lab.model;

public record ProductRecord(
        String name,
        int price,
        int quantity,
        String promotion
) {
    public static ProductRecord from(String record) {
        String[] fields = record.split(",");
        return new ProductRecord(fields[0], Integer.parseInt(fields[1]), Integer.parseInt(fields[2]), fields[3]);
    }
}
