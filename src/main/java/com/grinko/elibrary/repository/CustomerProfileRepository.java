package com.grinko.elibrary.repository;

import com.grinko.elibrary.entity.CustomerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Customer profile entity.
 */
@Repository
public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {

}
