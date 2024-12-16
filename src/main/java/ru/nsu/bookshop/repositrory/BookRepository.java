package ru.nsu.bookshop.repositrory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.nsu.bookshop.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {}
