package br.com.wswork.bestcommerceapi.repository;

import br.com.wswork.bestcommerceapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(nativeQuery = true,
            value = "SELECT * FROM products WHERE category = :category")
    List<Product> geAllProductsPerCategory(@Param("category") String category);
    Optional<Product> findByName(String name);
}