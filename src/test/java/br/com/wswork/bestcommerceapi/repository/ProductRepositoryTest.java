package br.com.wswork.bestcommerceapi.repository;

import br.com.wswork.bestcommerceapi.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testFindByName() {
        Product product = new Product();
        product.setName("Product Name");
        entityManager.persist(product);

        Optional<Product> foundProduct = productRepository.findByName("Product Name");

        assertEquals(product, foundProduct.orElse(null));
    }
}
