package com.grinko.elibrary.service;

import com.grinko.elibrary.entity.CustomerProfile;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Locale;

/**
 * Service class for managing customers profiles.
 */
public interface CustomerProfileService {

    boolean isExist(Long id);

    CustomerProfile find(Long id, Locale locale) throws EntityNotFoundException;

    void save(CustomerProfile customerProfile, Locale locale) throws EntityExistsException;

    void delete(Long id, Locale locale) throws EntityNotFoundException;
}
