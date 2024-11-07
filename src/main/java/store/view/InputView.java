package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.service.StoreService;

public class InputView {
    private final StoreService service = new StoreService();

    public void readItem() {
        while (true) {
            OutputView.printWelcomeMessage();
            OutputView.printStock(service.getStock());
            System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
            try {
                String input = Console.readLine();
                if (input.equals("N")) {
                    return;
                }
                service.run(input);
                System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
                String exitInput = Console.readLine();
                if (exitInput.equals("N")) {
                    return;
                }
            } catch (Exception e) {
                System.out.println("[ERROR] " + e.getMessage() + " 다시 입력해 주세요.");
            }
        }
    }
}
