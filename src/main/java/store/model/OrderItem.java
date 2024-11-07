package store.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record OrderItem(
        String name,
        int quantity
) {
    private static final Pattern ITEM_PATTERN = Pattern.compile("\\[(.+)-([1-9]\\d*)]");

    public static OrderItem from(String product) {
        Matcher matcher = ITEM_PATTERN.matcher(product);
        String name = matcher.group(1);
        int quantity = Integer.parseInt(matcher.group(2));
        return new OrderItem(name, quantity);
    }
}
