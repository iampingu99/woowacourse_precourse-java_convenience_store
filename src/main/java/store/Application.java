package store;

import java.util.List;
import store.controller.InputController;
import store.controller.PosController;
import store.controller.QuantityController;
import store.controller.StoreController;
import store.model.OrderItem;
import store.model.Stock;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.service.PosService;
import store.service.QuantityService;
import store.service.StockFactory;
import store.service.StockService;
import store.utils.FileReader;
import store.view.OutputView;


public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        FileReader fileReader = new FileReader();
        ProductRepository productRepository = new ProductRepository(fileReader);
        PromotionRepository promotionRepository = new PromotionRepository(fileReader);

        StockFactory stockFactory = new StockFactory(productRepository, promotionRepository);
        Stock stock = stockFactory.createStock();

        InputController inputController = new InputController();

        PosController posController = new PosController(inputController, new PosService());
        QuantityController quantityController = new QuantityController(inputController, new QuantityService());
        StoreController storeController = new StoreController(posController, quantityController,
                new StockService(stock));

        while (true) {
            try {
                OutputView.printWelcomeMessage();
                OutputView.printStock(stock);
                List<OrderItem> purchaseProduct = inputController.getPurchaseProduct();
                storeController.run(purchaseProduct);
                String input = inputController.confirmAdditionalPurchase();
                if (input.equals("N")) {
                    return;
                }
            } catch (IllegalArgumentException e) {
                OutputView.printError(e);
            }
        }
    }
}
