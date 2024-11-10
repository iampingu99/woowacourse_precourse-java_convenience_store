package lab.repository;

import java.util.HashMap;
import java.util.Map;
import lab.model.Promotion;
import lab.utils.FileReader;

public class PromotionRepository {

    private final String PROMOTIONS_FILE = "promotions.md";
    private final FileReader fileReader;

    public PromotionRepository(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    public Map<String, Promotion> loadPromotions() {
        Map<String, Promotion> promotions = new HashMap<>();
        fileReader.loadData(PROMOTIONS_FILE, line -> {
            Promotion promotion = Promotion.from(line);
            promotions.put(promotion.getName(), promotion);
        });
        return promotions;
    }
}
