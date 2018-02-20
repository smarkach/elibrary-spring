package com.grinko.elibrary.service;

import com.grinko.elibrary.entity.Customer;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Locale;

/**
 * Service class for managing users.
 */
public interface CustomerService {

    boolean isExist(Long id);

    boolean isEmailUsed(String email);

    Customer find(Long id, Locale locale) throws EntityNotFoundException;

    Customer findByEmail(String email, Locale locale) throws EntityNotFoundException;

    List<Customer> findAll();

    void save(Customer customer, Locale locale) throws EntityExistsException;

    void delete(Long id, Locale locale) throws EntityNotFoundException;
}
