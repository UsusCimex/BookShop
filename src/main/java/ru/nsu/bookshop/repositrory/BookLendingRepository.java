package ru.nsu.bookshop.repositrory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.nsu.bookshop.model.entity.BookLending;

@Repository
public interface BookLendingRepository extends JpaRepository<BookLending, Long> {
    List<BookLending> findByClientIdAndReturnDateIsNull(Long clientId);
    List<BookLending> findByBookIdAndReturnDateIsNull(Long bookId);
    List<BookLending> findByReturnDateIsNull();
}