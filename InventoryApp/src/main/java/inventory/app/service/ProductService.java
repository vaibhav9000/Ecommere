package inventory.app.service;

import inventory.app.model.Product;
import inventory.app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getProducts(String sqlQuery) {
        // Implement logic to handle SQL query and return products
        return productRepository.findAll(); // Placeholder
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(String id, Product product) {
        if (!productRepository.existsById(id)) {
            return null;
        }
        product.setId(id);
        return productRepository.save(product);
    }

    public boolean deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            return false;
        }
        productRepository.deleteById(id);
        return true;
    }
}
