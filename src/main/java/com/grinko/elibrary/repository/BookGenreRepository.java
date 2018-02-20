package com.grinko.elibrary.repository;

import com.grinko.elibrary.entity.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Book genre entity.
 */
@Repository
public interface BookGenreRepository extends JpaRepository<BookGenre, Long> {

}
