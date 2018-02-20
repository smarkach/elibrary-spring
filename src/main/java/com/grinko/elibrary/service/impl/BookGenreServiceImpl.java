package com.grinko.elibrary.service.impl;

import com.grinko.elibrary.entity.BookGenre;
import com.grinko.elibrary.repository.BookGenreRepository;
import com.grinko.elibrary.service.BookGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Locale;

@Service("bookGenreService")
@Transactional
public class BookGenreServiceImpl implements BookGenreService {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private BookGenreRepository bookGenreRepository;


    public boolean isExist(Long id) {
        return bookGenreRepository.exists(id);
    }

    public BookGenre find(Long id, Locale locale) throws EntityNotFoundException {
        BookGenre bookGenre = bookGenreRepository.findOne(id);
        if (bookGenre == null) {
            throw new EntityNotFoundException(messageSource.getMessage("service.exception.genre.not-found",
                    null, locale));
        }
        return bookGenre;
    }

    public void save(BookGenre bookGenre, Locale locale) throws EntityExistsException {
        if (bookGenre.getId() != null && isExist(bookGenre.getId())) {
            throw new EntityExistsException(messageSource.getMessage("service.exception.genre.id-exists",
                    null, locale));
        }
        bookGenreRepository.saveAndFlush(bookGenre);
    }

    public void delete(Long id, Locale locale) throws EntityNotFoundException {
        BookGenre bookGenre = bookGenreRepository.findOne(id);
        if (bookGenre == null) {
            throw new EntityNotFoundException(messageSource.getMessage("service.exception.genre.not-found",
                    null, locale));
        }
        bookGenreRepository.delete(id);
    }
}
