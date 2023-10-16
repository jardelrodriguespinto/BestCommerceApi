package br.com.wswork.bestcommerceapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "First name is required")
	private String firstName;

	@NotBlank(message = "Last name is required")
	private String lastName;

	@Positive(message = "Age must be a positive number")
	private int age;

	@OneToOne
	@JoinColumn(name = "customer_type_id")
	@NotNull(message = "Customer type is required")
	private CustomerType customerType;

	@OneToOne
	@NotNull(message = "Address is required")
	private Address address;
}