package br.com.wswork.bestcommerceapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "Name is required")
	private String name;

	@NotBlank(message = "Description is required")
	private String description;

	@ManyToOne
	@JoinColumn(name = "category_id")
	@NotNull(message = "Category is required")
	private Category category;

	@Positive(message = "Price must be a positive value")
	@NotNull(message = "Price is required")
	private BigDecimal price;

	@Positive(message = "Tax must be a positive value")
	private double tax;
}
