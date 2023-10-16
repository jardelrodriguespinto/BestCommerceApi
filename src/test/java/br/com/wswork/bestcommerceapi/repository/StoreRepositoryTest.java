package br.com.wswork.bestcommerceapi.repository;

import br.com.wswork.bestcommerceapi.model.Store;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StoreRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StoreRepository storeRepository;

    @Test
    public void testFindById() {
        Store store = new Store();
        store.setName("My Store");
        entityManager.persist(store);

        Optional<Store> optionalStore = storeRepository.findById(store.getId());

        assertTrue(optionalStore.isPresent());
        assertEquals(store, optionalStore.get());
    }

    @Test
    public void testFindByName() {
        Store store = new Store();
        store.setName("My Store");
        entityManager.persist(store);

        Optional<Store> optionalStore = storeRepository.findByName("My Store");

        assertTrue(optionalStore.isPresent());
        assertEquals(store, optionalStore.get());
    }

    @Test
    public void testFindByName_WithNonExistingName() {
        Optional<Store> optionalStore = storeRepository.findByName("Nonexistent Store");

        assertFalse(optionalStore.isPresent());
    }
}
