package br.com.wswork.bestcommerceapi.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.wswork.bestcommerceapi.model.Category;
import lombok.*;

@Builder
@Getter
public class CategoryDTO {

	Long id;
	String name;
	public static CategoryDTO convertEntityToDTO(Category category) {

		return CategoryDTO.builder()
    					  .id(category.getId())
						  .name(category.getName())
						  .build();
	}
	
	public static List<CategoryDTO> convertListEntityToDTO(List<Category> categories) {

		return categories.stream()
				         .map(CategoryDTO::convertEntityToDTO)
				         .collect(Collectors.toList());
	}
}