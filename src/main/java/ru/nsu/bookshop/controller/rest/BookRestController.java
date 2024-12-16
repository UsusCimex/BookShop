package ru.nsu.bookshop.controller.rest;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.nsu.bookshop.model.entity.Book;
import ru.nsu.bookshop.service.BookService;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookRestController {

    private final BookService bookService;

    @GetMapping
    public Page<Book> getBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String isbn,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookService.searchBooks(title, author, isbn, pageable);
    }

    @GetMapping("/all")
    public List<Book> findAll() {
        return bookService.findAll();
    }
    
    @PostMapping
    public void addBook(@RequestBody Book book) {
        bookService.add(book);
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable Long id, @RequestBody Book book) throws Exception {
        bookService.update(id, book).orElseThrow(() -> new Exception("Book not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
