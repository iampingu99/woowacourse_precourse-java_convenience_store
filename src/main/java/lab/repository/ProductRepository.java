package lab.repository;

import java.util.ArrayList;
import java.util.List;
import lab.model.ProductRecord;
import lab.utils.FileReader;

public class ProductRepository {

    private final String PRODUCTS_FILE = "products.md";
    private final FileReader fileReader;

    public ProductRepository(FileReader fileReader) {
        this.fileReader = fileReader;
    }
    
    public List<ProductRecord> loadProducts() {
        List<ProductRecord> products = new ArrayList<>();
        fileReader.loadData(PRODUCTS_FILE, line -> products.add(ProductRecord.from(line)));
        return products;
    }
}
