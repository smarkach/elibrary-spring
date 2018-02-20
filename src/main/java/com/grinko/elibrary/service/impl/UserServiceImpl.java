package com.grinko.elibrary.service.impl;

import com.grinko.elibrary.entity.Role;
import com.grinko.elibrary.entity.User;
import com.grinko.elibrary.repository.UserRepository;
import com.grinko.elibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Locale;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;


    public boolean isExist(Long id) {
        return userRepository.exists(id);
    }

    public boolean isUsernameUsed(String username) {
        return userRepository.findByUsername(username) != null;
    }

    public User find(Long id, Locale locale) throws EntityNotFoundException {
        User user = userRepository.findOne(id);
        if (user == null) {
            throw new EntityNotFoundException(messageSource.getMessage("service.exception.user.not-found",
                    null, locale));
        }
        return user;
    }

    public User findByUsername(String username, Locale locale) throws EntityNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException(messageSource.getMessage("service.exception.user.username-not-found",
                    null, locale));
        }
        return user;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void save(User user, Locale locale) throws EntityExistsException {
        if (user.getId() != null && isExist(user.getId())) {
            throw new EntityExistsException(messageSource.getMessage("service.exception.user.id-exists",
                    null, locale));
        }
        if (user.getUsername() != null && isUsernameUsed(user.getUsername())) {
            throw new EntityExistsException(messageSource.getMessage("service.exception.user.username-exists",
                    null, locale));
        }
        user.setUsername(user.getUsername().toLowerCase());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.getRoles().add(new Role(user, "ROLE_USER"));
        userRepository.saveAndFlush(user);
    }

    public void delete(Long id, Locale locale) throws EntityNotFoundException {
        User user = userRepository.findOne(id);
        if (user == null) {
            throw new EntityNotFoundException(messageSource.getMessage("service.exception.user.not-found",
                    null, locale));
        }
        userRepository.delete(id);
    }
}
