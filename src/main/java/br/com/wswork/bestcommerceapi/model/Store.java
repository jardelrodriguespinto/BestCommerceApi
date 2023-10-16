package br.com.wswork.bestcommerceapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stores")
public class Store {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "Store name is required")
	private String name;

	@OneToMany(mappedBy = "store")
	private List<Sale> sales;

	@ManyToMany
	@JoinTable(
			name = "store_customers",
			joinColumns = @JoinColumn(name = "store_id"),
			inverseJoinColumns = @JoinColumn(name = "customer_id")
	)
	private List<Customer> customers;

	@NotNull(message = "Store address is required")
	@OneToOne
	private Address address;
}
