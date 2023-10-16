package br.com.wswork.bestcommerceapi.dto;

import br.com.wswork.bestcommerceapi.model.Address;
import br.com.wswork.bestcommerceapi.model.Customer;
import br.com.wswork.bestcommerceapi.model.Sale;
import br.com.wswork.bestcommerceapi.model.Store;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class StoreDTO {

    Long id;
    String name;
    Address address;
    List<Sale> sales;
    List<Customer> customers;

    public static StoreDTO convertEntityToDTO(Store store) {

        return StoreDTO.builder()
                       .id(store.getId())
                       .name(store.getName())
                       .address(store.getAddress())
                       .sales(store.getSales())
                       .customers(store.getCustomers())
                       .build();
    }

    public static List<StoreDTO> convertListEntityToDTO(List<Store> stores) {

        return stores.stream()
                     .map(StoreDTO::convertEntityToDTO)
                     .collect(Collectors.toList());
    }
}