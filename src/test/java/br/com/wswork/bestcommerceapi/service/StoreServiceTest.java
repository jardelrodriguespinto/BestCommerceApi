package br.com.wswork.bestcommerceapi.service;

import br.com.wswork.bestcommerceapi.exception.StoreNotFoundException;
import br.com.wswork.bestcommerceapi.model.*;
import br.com.wswork.bestcommerceapi.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StoreServiceTest {

    @InjectMocks
    private StoreService storeService;

    @Mock
    private StoreRepository storeRepository;

    @BeforeEach
    public void setUp() {
        when(storeRepository.findAll()).thenReturn(Arrays.asList(
                new Store(1L, "Store 1",  new ArrayList<>(), new ArrayList<>(), new Address()),
                new Store(2L, "Store 2",  new ArrayList<>(), new ArrayList<>(), new Address())
        ));

        when(storeRepository.findById(1L)).thenReturn(Optional.of(
                new Store(1L, "Store 1",  new ArrayList<>(), new ArrayList<>(), new Address())
        ));

        when(storeRepository.findById(3L)).thenReturn(Optional.empty());
    }

    @Test
    public void testGetAllStores() {
        List<Store> stores = storeService.getAllStores();
        assertEquals(2, stores.size());
    }

    @Test
    public void testGetStoreById() {
        Store store = storeService.getStoreById(1L);
        assertNotNull(store);
        assertEquals(1L, store.getId());
    }

    @Test
    public void testGetStoreById_WhenStoreNotFound() {
        assertThrows(StoreNotFoundException.class, () -> storeService.getStoreById(3L));
    }

    @Test
    public void testAddStore() {
        Store newStore = new Store(1L, "Store 1",  new ArrayList<>(), new ArrayList<>(), new Address());

        when(storeRepository.save(newStore)).thenReturn(newStore);

        Store addedStore = storeService.addStore(newStore);

        assertNotNull(addedStore);
        assertEquals(3L, addedStore.getId());
    }

    @Test
    public void testModifyStore() {
        Store modifiedStore = new Store(1L, "Store 1",  new ArrayList<>(), new ArrayList<>(), new Address());
        when(storeRepository.findById(1L)).thenReturn(Optional.of(
                new Store(1L, "Store 1",  new ArrayList<>(), new ArrayList<>(), new Address())
        ));

        Store result = storeService.modifyStore(modifiedStore, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Modified Store 1", result.getName());
    }

    @Test
    public void testModifyStore_WhenStoreNotFound() {
        Store modifiedStore = new Store(1L, "Store 3",  new ArrayList<>(), new ArrayList<>(), new Address());
        when(storeRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(StoreNotFoundException.class, () -> storeService.modifyStore(modifiedStore, 3L));
    }

    @Test
    public void testDeleteStoreById() {
        when(storeRepository.findById(1L)).thenReturn(Optional.of(
                new Store(1L, "Store 1",  new ArrayList<>(), new ArrayList<>(), new Address())
        ));

        storeService.deleteStoreById(1L);

        Mockito.verify(storeRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteStoreById_WhenStoreNotFound() {
        when(storeRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(StoreNotFoundException.class, () -> storeService.deleteStoreById(3L));
    }
}
