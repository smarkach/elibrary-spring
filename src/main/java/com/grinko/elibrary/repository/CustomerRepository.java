package com.grinko.elibrary.repository;

import com.grinko.elibrary.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Customer entity.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByEmail(String email);

    @Query("select c from Customer c where c.email = ?1")
    Customer findByEmailUsingQuery(String email);

    @Query(value = "SELECT * FROM customers WHERE email = ?1", nativeQuery = true)
    Customer findByEmailUsingNativeQuery(String email);

    List<Customer> findAllByFirstNameAndLastName(String firstName, String lastName);

    @Query("select c from Customer c where c.firstName = ?1 and c.lastName = ?2")
    List<Customer> findAllByFirstNameAndLastNameUsingQuery(String firstName, String lastName);

    @Query(value = "SELECT * FROM customers WHERE first_name = ?1 AND last_Name = ?2", nativeQuery = true)
    List<Customer> findAllByFirstNameAndLastNameUsingNativeQuery(String firstName, String lastName);
}
