package com.grinko.elibrary.service.impl;

import com.grinko.elibrary.entity.CustomerProfile;
import com.grinko.elibrary.repository.CustomerProfileRepository;
import com.grinko.elibrary.service.CustomerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Locale;

@Service("customerProfileService")
@Transactional
public class CustomerProfileServiceImpl implements CustomerProfileService {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CustomerProfileRepository customerProfileRepository;


    public boolean isExist(Long id) {
        return customerProfileRepository.exists(id);
    }

    public CustomerProfile find(Long id, Locale locale) throws EntityNotFoundException {
        CustomerProfile customerProfile = customerProfileRepository.findOne(id);
        if (customerProfile == null) {
            throw new EntityNotFoundException(messageSource.getMessage("service.exception.profile.not-found",
                    null, locale));
        }
        return customerProfile;
    }

    public void save(CustomerProfile customerProfile, Locale locale) throws EntityExistsException {
        if (customerProfile.getId() != null && isExist(customerProfile.getId())) {
            throw new EntityExistsException(messageSource.getMessage("service.exception.profile.id-exists",
                    null, locale));
        }
        customerProfileRepository.saveAndFlush(customerProfile);
    }

    public void delete(Long id, Locale locale) throws EntityNotFoundException {
        CustomerProfile customerProfile = customerProfileRepository.findOne(id);
        if (customerProfile == null) {
            throw new EntityNotFoundException(messageSource.getMessage("service.exception.profile.not-found",
                    null, locale));
        }
        customerProfileRepository.delete(id);
    }
}
