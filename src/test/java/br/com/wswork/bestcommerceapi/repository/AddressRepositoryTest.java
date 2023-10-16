package br.com.wswork.bestcommerceapi.repository;

import br.com.wswork.bestcommerceapi.model.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AddressRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void testFindFullAddress() {
        Address address = new Address(1L,123, "Test Neighborhood", "Test State", "Test City", "Test Country");
        entityManager.persist(address);

        // Execute o método a ser testado
        Optional<Address> foundAddress = addressRepository.findFullAddress(
                address.getNumber(),
                address.getNeighbourhood(),
                address.getState(),
                address.getCity(),
                address.getCountry()
        );

        assertEquals(address, foundAddress.orElse(null));
    }
}