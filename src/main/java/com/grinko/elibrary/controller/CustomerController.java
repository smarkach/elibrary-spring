package com.grinko.elibrary.controller;

import com.grinko.elibrary.entity.Customer;
import com.grinko.elibrary.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Locale;

/**
 * REST controller for managing customers.
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;


    /**
     * Create a new customer.
     *
     * @param customer the customer to create.
     * @return the ResponseEntity with status 201 (Created) and with body containing
     * the new customer, or with status 409 (Conflict) if the ID or the email of the
     * customer is already in use.
     */
    @PostMapping("/")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer, Locale locale) {
        LOGGER.info("Creating customer: {}.", customer);
        try {
            customerService.save(customer, locale);
            return new ResponseEntity<>(customer, HttpStatus.CREATED);
        } catch (EntityExistsException e) {
            LOGGER.error("Unable to create customer. {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /**
     * Get customer by ID.
     *
     * @param id the ID of the customer to find.
     * @return the ResponseEntity with status 200 (OK) and with body containing
     * the customer found by ID, or with status 404 (Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable("id") Long id, Locale locale) {
        LOGGER.info("Fetching customer with ID {}.", id);
        try {
            Customer customer = customerService.find(id, locale);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            LOGGER.error("Customer with ID {} not found. {}", id, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get all customers.
     *
     * @return the ResponseEntity with status 200 (OK) and with body containing
     * all customers, or with status 204 (No Content).
     */
    @GetMapping("/")
    public ResponseEntity<?> getAllCustomers() {
        LOGGER.info("Fetching all customers.");
        List<Customer> customers = customerService.findAll();
        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    /**
     * Delete the customer by ID.
     *
     * @param id the ID of the customer to delete.
     * @return the ResponseEntity with status 204 (No Content) if the customer
     * deleted successfully, or with status 404 (Not Found) if the customer
     * could not be found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Long id, Locale locale) {
        LOGGER.info("Deleting customer with ID {}.", id);
        try {
            customerService.delete(id, locale);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            LOGGER.error("Unable to delete. {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
