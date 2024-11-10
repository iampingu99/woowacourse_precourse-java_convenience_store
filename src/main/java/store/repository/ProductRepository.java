package store.repository;

import java.util.ArrayList;
import java.util.List;
import store.dto.ProductDto;
import store.utils.FileReader;

public class ProductRepository {

    private final String PRODUCTS_FILE = "products.md";
    private final FileReader fileReader;

    public ProductRepository(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    public List<ProductDto> loadProducts() {
        List<ProductDto> products = new ArrayList<>();
        fileReader.loadData(PRODUCTS_FILE, line -> products.add(ProductDto.from(line)));
        return products;
    }
}
