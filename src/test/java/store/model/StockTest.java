package store.model;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StockTest {

    private static Stock stock;

    @BeforeAll
    static void setUp() {
        stock = new Stock(List.of(
                Product.from("콜라,1000,10,탄산2+1"),
                Product.from("콜라,1000,10,null"),
                Product.from("사이다,1000,8,탄산2+1"),
                Product.from("사이다,1000,7,null"),
                Product.from("오렌지주스,1800,9,MD추천상품"),
                Product.from("오렌지주스,1800,0,null"),
                Product.from("탄산수,1200,5,탄산2+1"),
                Product.from("탄산수,1200,0,null"),
                Product.from("물,500,10,null"),
                Product.from("비타민워터,1500,6,null"),
                Product.from("감자칩,1500,5,반짝할인"),
                Product.from("감자칩,1500,5,null"),
                Product.from("초코바,1200,5,MD추천상품"),
                Product.from("초코바,1200,5,null"),
                Product.from("에너지바,2000,5,null"),
                Product.from("정식도시락,6400,8,null"),
                Product.from("컵라면,1700,1,MD추천상품"),
                Product.from("컵라면,1700,10,null")
        ));
    }

    @Test
    @DisplayName("재고 출력")
    void print_stock() {
        assertSimpleTest(() -> {
            assertThat(stock.toString()).contains(
                    "- 콜라 1,000원 10개 탄산2+1",
                    "- 콜라 1,000원 10개",
                    "- 사이다 1,000원 8개 탄산2+1",
                    "- 사이다 1,000원 7개",
                    "- 오렌지주스 1,800원 9개 MD추천상품",
                    "- 오렌지주스 1,800원 재고 없음",
                    "- 탄산수 1,200원 5개 탄산2+1",
                    "- 탄산수 1,200원 재고 없음",
                    "- 물 500원 10개",
                    "- 비타민워터 1,500원 6개",
                    "- 감자칩 1,500원 5개 반짝할인",
                    "- 감자칩 1,500원 5개",
                    "- 초코바 1,200원 5개 MD추천상품",
                    "- 초코바 1,200원 5개",
                    "- 에너지바 2,000원 5개",
                    "- 정식도시락 6,400원 8개",
                    "- 컵라면 1,700원 1개 MD추천상품",
                    "- 컵라면 1,700원 10개");
        });
    }

    @Test
    @DisplayName("상품 재고 수량 계산")
    void calcProductQuantity() {
        assertSimpleTest(() -> {
            assertThat(stock.calcProductQuantity("콜라")).isEqualTo(20);
            assertThat(stock.calcProductQuantity("오렌지주스")).isEqualTo(9);
            assertThat(stock.calcProductQuantity("물")).isEqualTo(10);
            assertThat(stock.calcProductQuantity("비타민워터")).isEqualTo(6);
            assertThat(stock.calcProductQuantity("감자칩")).isEqualTo(10);
            assertThat(stock.calcProductQuantity("초코바")).isEqualTo(10);
            assertThat(stock.calcProductQuantity("에너지바")).isEqualTo(5);
            assertThat(stock.calcProductQuantity("정식도시락")).isEqualTo(8);
            assertThat(stock.calcProductQuantity("컵라면")).isEqualTo(11);
        });
    }
}
