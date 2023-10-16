package br.com.wswork.bestcommerceapi.dto;

import br.com.wswork.bestcommerceapi.model.CustomerType;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class CustomerTypeDTO {

    Long id;
    String description;

    public static CustomerTypeDTO convertEntityToDTO(CustomerType customerType) {

        return CustomerTypeDTO.builder()
                              .id(customerType.getId())
                              .description(customerType.getDescription())
                              .build();
    }

    public static List<CustomerTypeDTO> convertListEntityToDTO(List<CustomerType> customerTypes) {

        return customerTypes.stream()
                             .map(CustomerTypeDTO::convertEntityToDTO)
                             .collect(Collectors.toList());
    }
}