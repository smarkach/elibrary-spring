package com.grinko.elibrary.repository;

import com.grinko.elibrary.entity.Book;
import com.grinko.elibrary.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Book entity.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByOwner(Customer owner);

    List<Book> findAllByAuthor(String author);

    List<Book> findAllByTitle(String title);
}
