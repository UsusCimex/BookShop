package ru.nsu.bookshop.repositrory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.nsu.bookshop.model.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c WHERE " +
        "(:firstName IS NULL OR LOWER(c.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))) AND " +
        "(:lastName IS NULL OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))) AND " +
        "(:email IS NULL OR LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
        "(:phone IS NULL OR c.phone LIKE CONCAT('%', :phone, '%'))")
    Page<Client> searchClients(@Param("firstName") String firstName,
                            @Param("lastName") String lastName,
                            @Param("email") String email,
                            @Param("phone") String phone,
                            Pageable pageable);

}
