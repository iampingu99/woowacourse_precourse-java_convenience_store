package lab.controller;

import java.util.List;
import lab.model.OrderItem;
import lab.service.StoreService;
import lab.view.InputView;
import lab.view.OutputView;

public class StoreController {
    private final InputController inputController;
    private final InputView inputView;
    private final OutputView outputView;

    public StoreController(InputController inputController, InputView inputView, OutputView outputView) {
        this.inputController = inputController;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        StoreService storeService = new StoreService();
        outputView.printWelcomeMessage();
        outputView.printStock(storeService.getStock());
        List<OrderItem> purchaseProduct = inputController.getPurchaseProduct(inputView, outputView);
        System.out.println(purchaseProduct);
    }
}
