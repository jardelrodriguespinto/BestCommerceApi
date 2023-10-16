package br.com.wswork.bestcommerceapi.repository;

import br.com.wswork.bestcommerceapi.model.Address;
import br.com.wswork.bestcommerceapi.model.Customer;
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
public class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testFindCustomerByFullname() {
        Customer customer = new Customer(1L, "John", "Doe", 30, new CustomerType(), new Address());
        entityManager.persist(customer);

        Optional<Customer> foundCustomer = customerRepository.findCustomerByFullname(
                customer.getFirstName(),
                customer.getLastName()
        );

        assertEquals(customer, foundCustomer.orElse(null));
    }
}
