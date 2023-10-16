package br.com.wswork.bestcommerceapi.service;

import br.com.wswork.bestcommerceapi.exception.ProductNotFoundException;
import br.com.wswork.bestcommerceapi.exception.SaleNotFoundException;
import br.com.wswork.bestcommerceapi.exception.StoreNotFoundException;
import br.com.wswork.bestcommerceapi.model.Store;
import br.com.wswork.bestcommerceapi.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreService (StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<Store> getAllStores() {

        return storeRepository.findAll();
    }

    public Store getStoreById(Long id) {

        Optional<Store> store = storeRepository.findById(id);

        return store.orElseThrow(() -> new StoreNotFoundException("Store with id " + id + " not found."));
    }

    public Store getStoreByName(String name) {

        Optional<Store> store = storeRepository.findByName(name);

        return store.orElseThrow(() -> new StoreNotFoundException("Store with name " + name + " not found."));
    }

    public Store addStore(Store newStore) {

        Optional<Store> store = storeRepository.findByName(newStore.getName());

        if (store.isPresent())
            throw new StoreNotFoundException("Store already exists not found.");

        storeRepository.save(newStore);

        return newStore;
    }

    public Store modifyStore(Store modifiedStore, Long id) {

        Optional<Store> store = storeRepository.findById(id);

        if (store.isEmpty())
            throw new SaleNotFoundException("Store with id " + id + " not found.");

        store.get().setId(modifiedStore.getId());
        store.get().setName(modifiedStore.getName());
        store.get().setAddress(modifiedStore.getAddress());
        store.get().setSales(modifiedStore.getSales());
        store.get().setCustomers(modifiedStore.getCustomers());

        storeRepository.save(store.get());

        return store.get();
    }

    public void deleteStoreById(Long id) {

        Optional<Store> store = storeRepository.findById(id);

        if (store.isEmpty())
            throw new ProductNotFoundException("Store with id " + id + " not found.");

        storeRepository.deleteById(id);
    }
}

