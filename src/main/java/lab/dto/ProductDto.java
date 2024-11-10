package lab.dto;

public record ProductDto(
        String name,
        int price,
        int quantity,
        String promotion
) {
    public static ProductDto from(String record) {
        String[] fields = record.split(",");
        return new ProductDto(fields[0], Integer.parseInt(fields[1]), Integer.parseInt(fields[2]), fields[3]);
    }
}
