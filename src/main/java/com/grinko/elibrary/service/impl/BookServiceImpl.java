package com.grinko.elibrary.service.impl;

import com.grinko.elibrary.entity.Book;
import com.grinko.elibrary.repository.BookRepository;
import com.grinko.elibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Locale;

@Service("bookService")
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private BookRepository bookRepository;


    public boolean isExist(Long id) {
        return bookRepository.exists(id);
    }

    public Book find(Long id, Locale locale) throws EntityNotFoundException {
        Book book = bookRepository.findOne(id);
        if (book == null) {
            throw new EntityNotFoundException(messageSource.getMessage("service.exception.book.not-found",
                    null, locale));
        }
        return book;
    }

    public void save(Book book, Locale locale) throws EntityExistsException {
        if (book.getId() != null && isExist(book.getId())) {
            throw new EntityExistsException(messageSource.getMessage("service.exception.book.id-exists",
                    null, locale));
        }
        bookRepository.saveAndFlush(book);
    }

    public void delete(Long id, Locale locale) throws EntityNotFoundException {
        Book book = bookRepository.findOne(id);
        if (book == null) {
            throw new EntityNotFoundException(messageSource.getMessage("service.exception.user.not-found",
                    null, locale));
        }
        bookRepository.delete(id);
    }
}
