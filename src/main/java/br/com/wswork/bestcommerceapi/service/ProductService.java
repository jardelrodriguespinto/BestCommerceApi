package br.com.wswork.bestcommerceapi.service;

import br.com.wswork.bestcommerceapi.exception.ProductNotFoundException;
import br.com.wswork.bestcommerceapi.model.Product;
import br.com.wswork.bestcommerceapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService (ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    public List<Product> getAllProductsPerCategory(String category) {

        return productRepository.geAllProductsPerCategory(category);
    }

    public Product getProductById(Long id) {

        Optional<Product> product = productRepository.findById(id);

        return product.orElseThrow(() -> new ProductNotFoundException("CustomerType with id " + id + " not found."));
    }

    public Product getProductByName(String name) {

        Optional<Product> product = productRepository.findByName(name);

        return product.orElseThrow(() -> new ProductNotFoundException("Product with name " + name + " not found."));

    }
    public Product addProduct(Product newProduct) {

        Optional<Product> product = productRepository.findByName(newProduct.getName());

        if (product.isEmpty())
            throw new ProductNotFoundException("Product with name " + newProduct.getName() + " not found.");

        productRepository.save(newProduct);

        return newProduct;
    }

    public Product modifyProduct(Product modifiedProduct, Long id) {

        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty())
            throw new ProductNotFoundException("CustomerType with id " + id + " not found.");

        product.get().setId(modifiedProduct.getId());
        product.get().setName(modifiedProduct.getName());
        product.get().setDescription(modifiedProduct.getDescription());
        product.get().setCategory(modifiedProduct.getCategory());
        product.get().setPrice(modifiedProduct.getPrice());
        product.get().setTax(modifiedProduct.getTax());

        productRepository.save(product.get());

        return product.get();
    }

    public void deleteProductById(Long id) {

        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty())
            throw new ProductNotFoundException("Product with id " + id + " not found.");

        productRepository.deleteById(id);
    }
}