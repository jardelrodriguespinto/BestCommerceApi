package br.com.wswork.bestcommerceapi.service;

import br.com.wswork.bestcommerceapi.exception.AddressNotFoundException;
import br.com.wswork.bestcommerceapi.model.Address;
import br.com.wswork.bestcommerceapi.repository.AddressRepository;
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
public class AddressServiceTest {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressRepository addressRepository;

    @BeforeEach
    public void setUp() {
        // Configurar comportamento do repositório simulado (mock)
        when(addressRepository.findAll()).thenReturn(Arrays.asList(
                new Address(1L, 10, "Downtown", "CA", "Los Angeles", "USA"),
                new Address(2L, 40, "Suburb", "NY", "New York", "USA")
        ));

        when(addressRepository.findById(1L)).thenReturn(Optional.of(
                new Address(1L, 15, "Downtown", "CA", "Los Angeles", "USA")
        ));

        when(addressRepository.findById(3L)).thenReturn(Optional.empty());
    }

    @Test
    public void testGetAllAddresses() {
        List<Address> addresses = addressService.getAllAddresses();
        assertEquals(2, addresses.size());
    }

    @Test
    public void testGetAddressById() {
        Address address = addressService.getAddressById(1L);
        assertNotNull(address);
        assertEquals(1L, address.getId());
    }

    @Test
    public void testGetAddressById_WhenAddressNotFound() {
        assertThrows(AddressNotFoundException.class, () -> addressService.getAddressById(3L));
    }
}
