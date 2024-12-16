package ru.nsu.bookshop.repositrory;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.nsu.bookshop.model.entity.BookLending;

@Repository
public interface BookLendingRepository extends JpaRepository<BookLending, Long> {
    List<BookLending> findByReturnDateIsNull();

    @Query("SELECT bl FROM BookLending bl WHERE " +
           "(:clientName IS NULL OR LOWER(CONCAT(bl.client.firstName, ' ', bl.client.lastName)) LIKE LOWER(CONCAT('%', :clientName, '%'))) AND " +
           "(:bookTitle IS NULL OR LOWER(bl.book.title) LIKE LOWER(CONCAT('%', :bookTitle, '%'))) AND " +
           "(:returnStatus IS NULL OR (bl.returnDate IS NULL AND :returnStatus = true) OR (bl.returnDate IS NOT NULL AND :returnStatus = false))")
    Page<BookLending> searchLendings(@Param("clientName") String clientName,
                                     @Param("bookTitle") String bookTitle,
                                     @Param("returnStatus") Boolean returnStatus,
                                     Pageable pageable);
}