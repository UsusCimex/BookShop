package ru.nsu.bookshop.controller;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import ru.nsu.bookshop.model.entity.Book;
import ru.nsu.bookshop.service.BookService;

@Controller
@RequestMapping("/books")
@AllArgsConstructor
public class BookViewController {
    private BookService bookService;

    @GetMapping
    public String getBooks(@RequestParam(required = false) String title,
                           @RequestParam(required = false) String author,
                           @RequestParam(required = false) String isbn,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size,
                           Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> books = bookService.searchBooks(title, author, isbn, pageable);

        model.addAttribute("books", books.getContent());
        model.addAttribute("currentPage", books.getNumber());
        model.addAttribute("totalPages", books.getTotalPages());
        model.addAttribute("totalItems", books.getTotalElements());
        model.addAttribute("title", title);
        model.addAttribute("author", author);
        model.addAttribute("isbn", isbn);
        return "book/books";
    }

    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "book/add-book";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute Book book) {
        bookService.add(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable Long id, Model model) throws NotFoundException {
        Book book = bookService.findById(id).orElseThrow(() -> new NotFoundException());
        model.addAttribute("book", book);
        return "book/edit-book";
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute Book book) throws NotFoundException {
        bookService.update(id, book).orElseThrow(() -> new NotFoundException());
        return "redirect:/books";
    }
}
