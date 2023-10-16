package br.com.wswork.bestcommerceapi.dto;

import br.com.wswork.bestcommerceapi.model.Customer;
import br.com.wswork.bestcommerceapi.model.Product;
import br.com.wswork.bestcommerceapi.model.Sale;
import br.com.wswork.bestcommerceapi.model.Store;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class SaleDTO {

    Long id;
    Customer customer;
    Store store;
    List<Product> products;
    LocalDateTime saleDate;
    public static SaleDTO convertEntityToDTO(Sale sale) {

        return SaleDTO.builder()
                      .id(sale.getId())
                      .customer(sale.getCustomer())
                      .products(sale.getProducts())
                      .store(sale.getStore())
                      .saleDate(sale.getSaleDate())
                      .build();
    }

    public static List<SaleDTO> convertListEntityToDTO(List<Sale> sales) {

        return sales.stream()
                    .map(SaleDTO::convertEntityToDTO)
                    .collect(Collectors.toList());
    }
}