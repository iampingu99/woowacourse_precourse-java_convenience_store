package store.view;


import store.dto.ReceiptDto;
import store.model.Stock;

public class OutputView {
    private static final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.";
    private static final String ERROR = "[ERROR] ";
    private static final String TRY_AGAIN = " 다시 입력해 주세요.";

    public static void printWelcomeMessage() {
        System.out.println(WELCOME_MESSAGE);
    }

    public static void printStock(Stock stock) {
        System.out.println(stock);
    }

    public static void printError(Exception exception) {
        System.out.println(ERROR + exception.getMessage() + TRY_AGAIN);
    }

    public static void printReceipt(ReceiptDto receiptDto) {
        System.out.println(receiptDto.getView());
    }
}
