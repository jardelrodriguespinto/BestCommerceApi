package br.com.wswork.bestcommerceapi.service;

import br.com.wswork.bestcommerceapi.exception.CustomerAlreadyExistException;
import br.com.wswork.bestcommerceapi.exception.CustomerNotFoundException;
import br.com.wswork.bestcommerceapi.model.Address;
import br.com.wswork.bestcommerceapi.model.Customer;
import br.com.wswork.bestcommerceapi.model.CustomerType;
import br.com.wswork.bestcommerceapi.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(
                new Customer(1L, "John", "Doe", 30, new CustomerType(null, "Owner"),
                        new Address(null, 0, null, null, null, null)),
                new Customer(2L, "Alice", "Smith", 25, new CustomerType(null, "Owner"),
                        new Address(null, 0, null, null, null, null))
        ));

        when(customerRepository.findById(1L)).thenReturn(Optional.of(
                new Customer(1L, "John", "Doe", 30, new CustomerType(null, "Owner"),
                        new Address(null, 0, null, null, null, null))
        ));

        when(customerRepository.findById(3L)).thenReturn(Optional.empty());

        when(customerRepository.findCustomerByFullname("John", "Doe")).thenReturn(Optional.of(
                new Customer(1L, "John", "Doe", 30, new CustomerType(null, "Owner"),
                        new Address(null, 0, null, null, null, null))
        ));
    }

    @Test
    public void testGetAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        assertEquals(2, customers.size());
    }

    @Test
    public void testGetCustomerById() {
        Customer customer = customerService.getCustomerById(1L);
        assertNotNull(customer);
        assertEquals(1L, customer.getId());
    }

    @Test
    public void testGetCustomerById_WhenCustomerNotFound() {
        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(3L));
    }

    @Test
    public void testAddCustomer() {
        Customer newCustomer =  new Customer(null ,"John", "Doe", 30,
                new CustomerType(null, "Owner"),
                new Address(null, 0, null, null, null, null));
        when(customerRepository.findCustomerByFullname("Jane", "Doe")).thenReturn(Optional.empty());

        Customer addedCustomer = customerService.addCustomer(newCustomer);

        assertNotNull(addedCustomer);
        assertEquals("Jane", addedCustomer.getFirstName());
        assertEquals("Doe", addedCustomer.getLastName());
    }

    @Test
    public void testAddCustomer_WhenCustomerAlreadyExists() {
        Customer existingCustomer = new Customer(null ,"John", "Doe", 30,
                new CustomerType(null, "Owner"),
                new Address(null, 0, null, null, null, null));
        when(customerRepository.findCustomerByFullname("John", "Doe")).thenReturn(Optional.of(existingCustomer));

        assertThrows(CustomerAlreadyExistException.class, () -> customerService.addCustomer(existingCustomer));
    }
}
