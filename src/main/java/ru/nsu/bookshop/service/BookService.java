package ru.nsu.bookshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ru.nsu.bookshop.model.entity.Book;
import ru.nsu.bookshop.repositrory.BookRepository;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public Book add(Book book) {
        return bookRepository.save(book);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public Optional<Book> update(Long id, Book book) {
        return bookRepository.findById(id)
            .map(bookToUpdate -> {
                bookToUpdate.setTitle(book.getTitle());
                bookToUpdate.setAuthor(book.getAuthor());
                bookToUpdate.setIsbn(book.getIsbn());
                return bookRepository.save(bookToUpdate);
            });
    }
}
