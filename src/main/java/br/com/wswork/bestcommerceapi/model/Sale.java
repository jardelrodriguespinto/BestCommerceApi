package br.com.wswork.bestcommerceapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sales")
public class Sale {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@NotNull(message = "Customer is required")
	private Customer customer;

	@ManyToOne
	@NotNull(message = "Store is required")
	private Store store;

	@ManyToMany
	@NotNull(message = "Products are required")
	private List<Product> products;

	@Column(columnDefinition = "TIMESTAMP")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message = "Sale date is required")
	private LocalDateTime saleDate;
}