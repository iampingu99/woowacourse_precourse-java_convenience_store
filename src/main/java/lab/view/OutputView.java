package lab.view;


import lab.model.Stock;

public class OutputView {
    private final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.";
    private final String ERROR = "[ERROR] ";

    public void printWelcomeMessage() {
        System.out.println(WELCOME_MESSAGE);
    }

    public void printStock(Stock stock) {
        System.out.println(stock);
    }

    public void printError(Exception exception) {
        System.out.println(ERROR + exception.getMessage());
    }
}
