package store;

import java.util.List;
import lab.controller.InputController;
import lab.controller.PosController;
import lab.controller.QuantityController;
import lab.controller.StoreController;
import lab.model.OrderItem;
import lab.model.Receipt;
import lab.model.Stock;
import lab.repository.ProductRepository;
import lab.repository.PromotionRepository;
import lab.service.PosService;
import lab.service.StockFactory;
import lab.service.StockService;
import lab.utils.FileReader;
import lab.view.OutputView;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        FileReader fileReader = new FileReader();
        ProductRepository productRepository = new ProductRepository(fileReader);
        PromotionRepository promotionRepository = new PromotionRepository(fileReader);

        StockFactory stockFactory = new StockFactory(productRepository, promotionRepository);
        Stock stock = stockFactory.createStock();

        PosController posController = new PosController(new PosService(new Receipt()));
        QuantityController quantityController = new QuantityController();
        StoreController storeController = new StoreController(posController, quantityController,
                new StockService(stock));

        InputController inputController = new InputController();

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
