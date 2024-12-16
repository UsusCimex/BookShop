package ru.nsu.bookshop.repositrory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.nsu.bookshop.model.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE " +
        "(:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
        "(:author IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))) AND " +
        "(:isbn IS NULL OR b.isbn LIKE CONCAT('%', :isbn, '%'))")
    Page<Book> searchBooks(@Param("title") String title,
                        @Param("author") String author,
                        @Param("isbn") String isbn,
                        Pageable pageable);

}
