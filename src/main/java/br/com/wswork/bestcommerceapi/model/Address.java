package br.com.wswork.bestcommerceapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "addresses")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull(message = "Number is required")
	@PositiveOrZero(message = "Number must be non-negative")
	private int number;

	@NotBlank(message = "Neighbourhood is required")
	@Size(max = 255, message = "Neighbourhood cannot exceed 255 characters")
	private String neighbourhood;

	@NotBlank(message = "State is required")
	@Size(max = 255, message = "State cannot exceed 255 characters")
	private String state;

	@NotBlank(message = "City is required")
	@Size(max = 255, message = "City cannot exceed 255 characters")
	private String city;

	@NotBlank(message = "Country is required")
	@Size(max = 255, message = "Country cannot exceed 255 characters")
	private String country;
}