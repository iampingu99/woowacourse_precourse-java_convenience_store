package lab.service;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import lab.dto.ReceiptDto;
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

    @Test
    void 포스기_영수증_출력() {
        assertSimpleTest(() -> {
            Map<ProductType, Integer> colaPurchaseQuantities = new HashMap<>();
            colaPurchaseQuantities.put(ProductType.PROMOTION, 3);
            Product cola = new Product("콜라", 1000);
            Promotion sodaPromotion = Promotion.from("탄산2+1,2,1,2024-01-01,2024-12-31");
            cola.setPromotion(sodaPromotion);

            Map<ProductType, Integer> energyBarPurchaseQuantities = new HashMap<>();
            energyBarPurchaseQuantities.put(ProductType.REGULAR, 5);
            Product energyBar = new Product("에너지바", 2000);

            Receipts receipts = new Receipts();
            PosService posService = new PosService(receipts);

            posService.addPurchaseProduct(colaPurchaseQuantities, cola);
            posService.addPurchaseProduct(energyBarPurchaseQuantities, energyBar);

            posService.addFreeProduct(colaPurchaseQuantities, cola);

            ReceiptDto receiptDto = posService.createReceiptDto("Y");

            assertThat(receiptDto.getView()).contains(
                    "===========W 편의점=============",
                    "상품명\t\t수량\t금액",
                    "콜라\t\t3 \t3,000",
                    "에너지바\t\t5 \t10,000",
                    "===========증\t정=============",
                    "콜라\t\t1",
                    "==============================",
                    "총구매액\t\t8\t13,000",
                    "행사할인\t\t\t-1,000",
                    "멤버십할인\t\t\t-3,000",
                    "내실돈\t\t\t 9,000"
            );
        });
    }
}
