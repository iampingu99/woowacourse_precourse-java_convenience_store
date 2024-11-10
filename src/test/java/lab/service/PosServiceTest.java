package lab.service;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import lab.model.Product;
import lab.model.ProductType;
import lab.model.Promotion;
import lab.model.Receipts;
import org.junit.jupiter.api.Test;

class PosServiceTest {

    @Test
    void 포스기에_구매_물건_등록() {
        assertSimpleTest(() -> {
            Map<ProductType, Integer> purchaseQuantities = new HashMap<>();
            purchaseQuantities.put(ProductType.PROMOTION, 10);
            purchaseQuantities.put(ProductType.REGULAR, 5);
            Product product = new Product("콜라", 1000);

            Receipts receipts = new Receipts();
            PosService posService = new PosService(receipts);
            posService.addPurchaseProduct(purchaseQuantities, product);

            assertThat(receipts.getPurchaseProducts().get(0).getQuantities()).isEqualTo(purchaseQuantities);
        });
    }

    @Test
    void 포스기에_증정_물건_등록() {
        assertSimpleTest(() -> {
            Map<ProductType, Integer> purchaseQuantities = new HashMap<>();
            purchaseQuantities.put(ProductType.PROMOTION, 10);
            purchaseQuantities.put(ProductType.REGULAR, 5);
            Product product = new Product("콜라", 1000);
            Promotion promotion = Promotion.from("탄산2+1,2,1,2024-01-01,2024-12-31");
            product.setPromotion(promotion);

            Receipts receipts = new Receipts();
            PosService posService = new PosService(receipts);
            posService.addFreeProduct(purchaseQuantities, product);

            assertThat(receipts.getFreeProducts().get(0).getQuantities().get(ProductType.PROMOTION)).isEqualTo(3);
        });
    }
}
