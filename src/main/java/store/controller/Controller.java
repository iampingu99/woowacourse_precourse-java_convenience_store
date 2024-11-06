package store.controller;

import store.model.Stock;
import store.view.OutputView;

public class Controller {
    private final Stock stock;

    public Controller(Stock stock) {
        this.stock = stock;
    }

    public void visit() {
        OutputView.printWelcomeMessage();
        OutputView.printStock(stock);
    }
}
