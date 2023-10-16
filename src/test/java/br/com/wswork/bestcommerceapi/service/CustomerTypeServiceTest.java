package br.com.wswork.bestcommerceapi.service;

import br.com.wswork.bestcommerceapi.exception.CustomerTypeAlreadyExistsException;
import br.com.wswork.bestcommerceapi.exception.CustomerTypeNotFoundException;
import br.com.wswork.bestcommerceapi.model.CustomerType;
import br.com.wswork.bestcommerceapi.repository.CustomerTypeRepository;
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
public class CustomerTypeServiceTest {

    @InjectMocks
    private CustomerTypeService customerTypeService;

    @Mock
    private CustomerTypeRepository customerTypeRepository;

    @BeforeEach
    public void setUp() {
        when(customerTypeRepository.findAll()).thenReturn(Arrays.asList(
                new CustomerType(1L, "Regular"),
                new CustomerType(2L, "Premium")
        ));

        when(customerTypeRepository.findById(1L)).thenReturn(Optional.of(
                new CustomerType(1L, "Regular")
        ));

        when(customerTypeRepository.findById(3L)).thenReturn(Optional.empty());

        when(customerTypeRepository.findCustomerTypeByDescription("Regular")).thenReturn(Optional.of(
                new CustomerType(1L, "Regular")
        ));
    }

    @Test
    public void testGetAllCustomerTypes() {
        List<CustomerType> customerTypes = customerTypeService.getAllCustomerTypes();
        assertEquals(2, customerTypes.size());
    }

    @Test
    public void testGetCustomerTypeById() {
        CustomerType customerType = customerTypeService.getCustomerTypeById(1L);
        assertNotNull(customerType);
        assertEquals(1L, customerType.getId());
    }

    @Test
    public void testGetCustomerTypeById_WhenCustomerTypeNotFound() {
        assertThrows(CustomerTypeNotFoundException.class, () -> customerTypeService.getCustomerTypeById(3L));
    }

    @Test
    public void testAddCustomerType() {
        CustomerType newCustomerType = new CustomerType(null, "Guest");
        when(customerTypeRepository.findCustomerTypeByDescription("Guest")).thenReturn(Optional.empty());

        CustomerType addedCustomerType = customerTypeService.addCustomerType(newCustomerType);

        assertNotNull(addedCustomerType);
        assertEquals("Guest", addedCustomerType.getDescription());
    }

    @Test
    public void testAddCustomerType_WhenCustomerTypeAlreadyExists() {
        CustomerType existingCustomerType = new CustomerType(null, "Regular");
        when(customerTypeRepository.findCustomerTypeByDescription("Regular")).thenReturn(Optional.of(existingCustomerType));

        assertThrows(CustomerTypeAlreadyExistsException.class, () -> customerTypeService.addCustomerType(existingCustomerType));
    }
}
