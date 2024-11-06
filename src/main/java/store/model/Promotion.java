package store.model;

import java.time.LocalDate;
import java.util.Objects;

public class Promotion {
    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate start_date;
    private final LocalDate end_date;

    public Promotion(String name, int buy, int get, LocalDate start_date, LocalDate end_date) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public static Promotion of(String record) {
        String[] fields = record.split(",");
        return new Promotion(
                fields[0],
                Integer.parseInt(fields[1]),
                Integer.parseInt(fields[2]),
                LocalDate.parse(fields[3]),
                LocalDate.parse(fields[4]));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Promotion promotion)) {
            return false;
        }
        return Objects.equals(name, promotion.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
