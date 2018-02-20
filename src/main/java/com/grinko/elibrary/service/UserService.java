package com.grinko.elibrary.service;

import com.grinko.elibrary.entity.User;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Locale;

/**
 * Service class for managing users.
 */
public interface UserService {

    boolean isExist(Long id);

    boolean isUsernameUsed(String username);

    User find(Long id, Locale locale) throws EntityNotFoundException;

    User findByUsername(String email, Locale locale) throws EntityNotFoundException;

    List<User> findAll();

    void save(User user, Locale locale) throws EntityExistsException;

    void delete(Long id, Locale locale) throws EntityNotFoundException;
}
