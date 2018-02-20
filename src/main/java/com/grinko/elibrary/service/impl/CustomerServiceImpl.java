package com.grinko.elibrary.service.impl;

import com.grinko.elibrary.entity.Customer;
import com.grinko.elibrary.repository.CustomerRepository;
import com.grinko.elibrary.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Locale;

@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EntityManager entityManager;


    public boolean isExist(Long id) {
        return customerRepository.exists(id);
    }

    public boolean isEmailUsed(String email) {
        return customerRepository.findByEmail(email) != null;
    }

    public Customer find(Long id, Locale locale) throws EntityNotFoundException {
        Customer customer = customerRepository.findOne(id);
        if (customer == null) {
            throw new EntityNotFoundException(messageSource.getMessage("service.exception.customer.not-found",
                    null, locale));
        }
        return customer;
    }

    public Customer findByEmail(String email, Locale locale) throws EntityNotFoundException {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new EntityNotFoundException(messageSource.getMessage("service.exception.customer.email-not-found",
                    null, locale));
        }
        return customer;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public void findByEmailUsingEM(String email, Locale locale) throws EntityNotFoundException {
        try {
            entityManager.createQuery("select c from Customer c where c.email = :email", Customer.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException(messageSource.getMessage("service.exception.customer.email-not-found",
                    null, locale));
        }
    }

    public void save(Customer customer, Locale locale) throws EntityExistsException {
        if (customer.getId() != null && isExist(customer.getId())) {
            throw new EntityExistsException(messageSource.getMessage("service.exception.customer.id-exists",
                    null, locale));
        }
        if (customer.getEmail() != null && isEmailUsed(customer.getEmail())) {
            throw new EntityExistsException(messageSource.getMessage("service.exception.customer.email-exists",
                    null, locale));
        }
        customer.setEmail(customer.getEmail().toLowerCase());
        customerRepository.saveAndFlush(customer);
    }

    public void delete(Long id, Locale locale) throws EntityNotFoundException {
        Customer customer = customerRepository.findOne(id);
        if (customer == null) {
            throw new EntityNotFoundException(messageSource.getMessage("service.exception.customer.not-found",
                    null, locale));
        }
        customerRepository.delete(id);
    }
}
