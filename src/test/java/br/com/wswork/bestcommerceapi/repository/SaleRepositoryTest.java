package br.com.wswork.bestcommerceapi.repository;

import br.com.wswork.bestcommerceapi.model.Sale;
import br.com.wswork.bestcommerceapi.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SaleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SaleRepository saleRepository;

    @Test
    public void testFindAllSalesByCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        entityManager.persist(customer);

        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setSaleDate(LocalDateTime.now());
        entityManager.persist(sale);

        List<Sale> salesByCustomer = saleRepository.findAllSalesByCustomer(customer.getId());

        assertEquals(1, salesByCustomer.size());
        assertEquals(sale, salesByCustomer.get(0));
    }

    @Test
    public void testFindAllSalesByCustomer_WithNoSales() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Jane");
        customer.setLastName("Smith");
        entityManager.persist(customer);

        List<Sale> salesByCustomer = saleRepository.findAllSalesByCustomer(customer.getId());

        assertEquals(0, salesByCustomer.size());
    }
}
