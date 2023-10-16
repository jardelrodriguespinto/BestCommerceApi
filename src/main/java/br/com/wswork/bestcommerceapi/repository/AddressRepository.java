package br.com.wswork.bestcommerceapi.repository;

import br.com.wswork.bestcommerceapi.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query(nativeQuery = true,
            value = "SELECT * FROM addresses " +
                    " WHERE number = :number AND" +
                    " number = :number AND" +
                    " neighbourhood = :neighbourhood AND" +
                    " state = :state AND" +
                    " city = :city AND" +
                    " country = :country")
    Optional<Address> findFullAddress(
            @Param("number") int number,
            @Param("neighbourhood") String neighbourhood,
            @Param("state") String state,
            @Param("city") String city,
            @Param("country") String country
    );
}

