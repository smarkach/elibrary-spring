package com.grinko.elibrary.service;

import com.grinko.elibrary.entity.BookGenre;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Locale;

/**
 * Service class for managing books genres.
 */
public interface BookGenreService {

    boolean isExist(Long id);

    BookGenre find(Long id, Locale locale) throws EntityNotFoundException;

    void save(BookGenre bookGenre, Locale locale) throws EntityExistsException;

    void delete(Long id, Locale locale) throws EntityNotFoundException;
}
