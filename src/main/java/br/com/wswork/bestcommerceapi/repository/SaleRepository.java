package br.com.wswork.bestcommerceapi.repository;

import br.com.wswork.bestcommerceapi.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM sales WHERE customer_id = :id")
    List<Sale> findAllSalesByCustomer(@Param("id") Long id);
}