package br.com.wswork.bestcommerceapi.service;

import br.com.wswork.bestcommerceapi.exception.ProductNotFoundException;
import br.com.wswork.bestcommerceapi.model.Category;
import br.com.wswork.bestcommerceapi.model.Product;
import br.com.wswork.bestcommerceapi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(
                new Product(1L, "Product1", "Description1", new Category(null, "NewCategory"), new BigDecimal("300.0"), 10.0),
                new Product(2L, "Product2", "Description2", new Category(null, "NewCategory"), new BigDecimal("300.0"), 20.0)
        ));

        when(productRepository.findById(1L)).thenReturn(Optional.of(
                new Product(1L, "Product1", "Description1", new Category(null, "NewCategory"), new BigDecimal("100.0"), 10.0)
        ));

        when(productRepository.findById(3L)).thenReturn(Optional.empty());

        when(productRepository.findByName("Product1")).thenReturn(Optional.of(
                new Product(1L, "Product1", "Description1", new Category(null, "NewCategory"), new BigDecimal("100.0"), 10.0)
        ));
    }

    @Test
    public void testGetAllProducts() {
        List<Product> products = productService.getAllProducts();
        assertEquals(2, products.size());
    }

    @Test
    public void testGetAllProductsPerCategory() {
        var category = new Category(null, "NewCategory");
        when(productRepository.geAllProductsPerCategory(category.getName())).thenReturn(List.of(
                new Product(1L, "Product1", "Description1", new Category(null, "NewCategory"), new BigDecimal("100.0"), 10.0)
        ));

        List<Product> products = productService.getAllProductsPerCategory(category.getName());
        assertEquals(1, products.size());
        assertEquals(category, products.get(0).getCategory());
    }

    @Test
    public void testGetProductById() {
        Product product = productService.getProductById(1L);
        assertNotNull(product);
        assertEquals(1L, product.getId());
    }

    @Test
    public void testGetProductById_WhenProductNotFound() {
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(3L));
    }

    @Test
    public void testGetProductByName() {
        String productName = "Product1";
        Product product = productService.getProductByName(productName);
        assertNotNull(product);
        assertEquals(productName, product.getName());
    }

    @Test
    public void testGetProductByName_WhenProductNotFound() {
        String nonExistingProductName = "NonExistingProduct";
        when(productRepository.findByName(nonExistingProductName)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductByName(nonExistingProductName));
    }

    @Test
    public void testAddProduct() {
    Product newProduct = new Product(null, "New Product", "NewDescription", new Category(null, "NewCategory"), new BigDecimal("300.0"), 30.0);
        when(productRepository.findByName("NewProduct")).thenReturn(Optional.empty());

        Product addedProduct = productService.addProduct(newProduct);

        assertNotNull(addedProduct);
        assertEquals("NewProduct", addedProduct.getName());
    }

    @Test
    public void testAddProduct_WhenProductAlreadyExists() {
        Product existingProduct = new Product(null, "New Product", "NewDescription", new Category(null, "NewCategory"), new BigDecimal("300.0"), 30.0);
        when(productRepository.findByName("Product1")).thenReturn(Optional.of(existingProduct));

        assertThrows(ProductNotFoundException.class, () -> productService.addProduct(existingProduct));
    }

    @Test
    public void testModifyProduct() {
        Product modifiedProduct = new Product(1L, "New Product", "NewDescription", new Category(null, "NewCategory"), new BigDecimal("300.0"), 30.0);
        when(productRepository.findById(1L)).thenReturn(Optional.of(
                new Product(null, "New Product", "NewDescription", new Category(null, "NewCategory"), new BigDecimal("300.0"), 30.0)
        ));

        Product result = productService.modifyProduct(modifiedProduct, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("ModifiedProduct", result.getName());
        assertEquals("ModifiedDescription", result.getDescription());
        assertEquals( new Category(null, "NewCategory"), result.getCategory());
        assertEquals(new BigDecimal("400.0"), result.getPrice());
        assertEquals(40.0, result.getTax());
    }

    @Test
    public void testModifyProduct_WhenProductNotFound() {
        Product modifiedProduct = new Product(3L, "New Product", "NewDescription", new Category(null, "NewCategory"), new BigDecimal("300.0"), 30.0);
        when(productRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.modifyProduct(modifiedProduct, 3L));
    }

    @Test
    public void testDeleteProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(
                new Product(1L, "New Product", "NewDescription", new Category(null, "NewCategory"), new BigDecimal("300.0"), 30.0)
        ));

        productService.deleteProductById(1L);

        Mockito.verify(productRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteProductById_WhenProductNotFound() {
        when(productRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProductById(3L));
    }
}
