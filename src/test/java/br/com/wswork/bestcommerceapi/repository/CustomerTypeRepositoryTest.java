package br.com.wswork.bestcommerceapi.repository;

import br.com.wswork.bestcommerceapi.model.CustomerType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CustomerTypeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerTypeRepository customerTypeRepository;

    @Test
    public void testFindCustomerTypeByDescription() {
        CustomerType customerType = new CustomerType(1L,"Description");
        entityManager.persist(customerType);

        Optional<CustomerType> foundCustomerType = customerTypeRepository.findCustomerTypeByDescription(customerType.getDescription());

        assertEquals(customerType, foundCustomerType.orElse(null));
    }
}