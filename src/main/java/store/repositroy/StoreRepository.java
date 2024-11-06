package store.repositroy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import store.model.Promotion;

public class StoreRepository {

    public List<Promotion> getPromotions() {
        List<Promotion> promotions = new ArrayList<>();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("promotions.md");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        ) {
            String format = reader.readLine();
            reader.lines().forEach(line -> promotions.add(Promotion.of(line)));
        } catch (IOException e) {
            System.out.println("파일을 읽는 중 오류가 발생했습니다: " + e.getMessage());
        }
        return promotions;
    }
}
