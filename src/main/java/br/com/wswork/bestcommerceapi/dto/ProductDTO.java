package br.com.wswork.bestcommerceapi.dto;

import br.com.wswork.bestcommerceapi.model.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class ProductDTO {

    Long id;
    String name;
    String description;
    Category category;
    BigDecimal price;
    double tax;

    public static ProductDTO convertEntityToDTO(Product product) {

        return ProductDTO.builder()
                         .id(product.getId())
                         .name(product.getName())
                         .description(product.getDescription())
                         .category(product.getCategory())
                         .price(product.getPrice())
                         .tax(product.getTax())
                         .build();
    }

    public static List<ProductDTO> convertListEntityToDTO(List<Product> products) {

        return products.stream()
                       .map(ProductDTO::convertEntityToDTO)
                       .collect(Collectors.toList());
    }
}