package com.grinko.elibrary.service;

import com.grinko.elibrary.entity.Book;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Locale;

/**
 * Service class for managing books.
 */
public interface BookService {

    boolean isExist(Long id);

    Book find(Long id, Locale locale) throws EntityNotFoundException;

    void save(Book book, Locale locale) throws EntityExistsException;

    void delete(Long id, Locale locale) throws EntityNotFoundException;
}
