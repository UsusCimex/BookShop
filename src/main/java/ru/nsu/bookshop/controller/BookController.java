package ru.nsu.bookshop.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ru.nsu.bookshop.model.Book;
import ru.nsu.bookshop.service.BookService;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<Book> getAll() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public Book getById(Long id) {
        return bookService.findById(id);
    }

    @PostMapping("/{id}")
    public Book updateById(Long id, Book book) {
        return bookService.update(id, book);
    }

    @DeleteMapping("/{id}")
    public void deleteById(Long id) {
        bookService.deleteById(id);
    }
}
