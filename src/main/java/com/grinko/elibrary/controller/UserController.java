package com.grinko.elibrary.controller;

import com.grinko.elibrary.entity.User;
import com.grinko.elibrary.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Locale;

/**
 * REST controller for managing users.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService userService;


    /**
     * Create a new user.
     *
     * @param user the user to create.
     * @return the ResponseEntity with status 201 (Created) and with body containing
     * the new user, or with status 409 (Conflict) if the ID or the username of the
     * user is already in use.
     */
    @PostMapping("/")
    public ResponseEntity<?> createUser(@RequestBody User user, Locale locale) {
        LOGGER.info(messageSource.getMessage("controller.log.user.creating", new Object[]{user}, locale));
        try {
            userService.save(user, locale);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (EntityExistsException e) {
            LOGGER.error(messageSource.getMessage("controller.log.user.creating-unable",
                    new Object[]{e.getMessage()}, locale));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    /**
     * Get user by ID.
     *
     * @param id the ID of the user to find.
     * @return the ResponseEntity with status 200 (OK) and with body containing
     * the user found by ID, or with status 404 (Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id, Locale locale) {
        LOGGER.info(messageSource.getMessage("controller.log.user.fetching", new Object[]{id}, locale));
        try {
            User user = userService.find(id, locale);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            LOGGER.error(messageSource.getMessage("controller.log.user.not-found",
                    new Object[]{id, e.getMessage()}, locale));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Get all users.
     *
     * @return the ResponseEntity with status 200 (OK) and with body containing
     * all users, or with status 204 (No Content).
     */
    @GetMapping("/")
    public ResponseEntity<?> getAllUsers(Locale locale) {
        LOGGER.info(messageSource.getMessage("controller.log.user.fetching-all", null, locale));
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Delete the user by ID.
     *
     * @param id the ID of the user to delete.
     * @return the ResponseEntity with status 204 (No Content) if the user
     * deleted successfully, or with status 404 (Not Found) if the user
     * could not be found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Long id, Locale locale) {
        LOGGER.info(messageSource.getMessage("controller.log.user.deleting", new Object[]{id}, locale));
        try {
            userService.delete(id, locale);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            LOGGER.error(messageSource.getMessage("controller.log.user.deleting-unable", null, locale));
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
