package lab.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import lab.model.Promotion;
import lab.model.Stock;


public class StoreRepository {

    public Map<String, Promotion> getPromotions() {
        Map<String, Promotion> promotions = new HashMap<>();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("promotions.md");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        ) {
            String format = reader.readLine();
            reader.lines().forEach(line -> {
                Promotion promotion = Promotion.from(line);
                promotions.put(promotion.getName(), promotion);
            });
        } catch (IOException e) {
            System.out.println("파일을 읽는 중 오류가 발생했습니다: " + e.getMessage());
        }
        return promotions;
    }

    public Stock getProduct(Stock stock) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("products.md");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        ) {
            String header = reader.readLine();
            reader.lines().forEach(stock::addProduct);
        } catch (IOException e) {
            System.out.println("파일을 읽는 중 오류가 발생했습니다: " + e.getMessage());
        }
        return stock;
    }
}
