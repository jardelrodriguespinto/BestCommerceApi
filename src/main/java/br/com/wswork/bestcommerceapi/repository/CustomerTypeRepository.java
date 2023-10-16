package br.com.wswork.bestcommerceapi.repository;

import br.com.wswork.bestcommerceapi.model.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerTypeRepository extends JpaRepository<CustomerType, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM customer_types WHERE description :description")
    Optional<CustomerType> findCustomerTypeByDescription(@Param("description") String description);
}