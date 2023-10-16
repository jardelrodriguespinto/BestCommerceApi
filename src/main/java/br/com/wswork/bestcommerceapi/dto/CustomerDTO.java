package br.com.wswork.bestcommerceapi.dto;

import br.com.wswork.bestcommerceapi.model.Address;
import br.com.wswork.bestcommerceapi.model.Customer;
import br.com.wswork.bestcommerceapi.model.CustomerType;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class CustomerDTO {

    Long id;
    String name;
    int age;
    CustomerType customerType;
    Address address;

    public static CustomerDTO convertEntityToDTO(Customer customer) {

        return CustomerDTO.builder()
                           .id(customer.getId())
                           .name(customer.getFirstName())
                           .name(customer.getLastName())
                           .age(customer.getAge())
                           .customerType(customer.getCustomerType())
                           .build();
    }

    public static List<CustomerDTO> convertListEntityToDTO(List<Customer> customers) {

        return customers.stream()
                        .map(CustomerDTO::convertEntityToDTO)
                        .collect(Collectors.toList());
    }
}