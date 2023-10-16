package br.com.wswork.bestcommerceapi.repository;

import br.com.wswork.bestcommerceapi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM customers WHERE firstName = :firstName AND lastName = :lastName")
    Optional<Customer> findCustomerByFullname(@Param("firstName") String firstName, @Param("lastName") String lastName);
}


