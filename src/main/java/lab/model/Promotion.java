package lab.model;

import java.time.LocalDate;

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

    public String getName() {
        return name;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public static Promotion from(String record) {
        String[] fields = record.split(",");
        return new Promotion(
                fields[0],
                Integer.parseInt(fields[1]),
                Integer.parseInt(fields[2]),
                LocalDate.parse(fields[3]),
                LocalDate.parse(fields[4]));
    }
}
