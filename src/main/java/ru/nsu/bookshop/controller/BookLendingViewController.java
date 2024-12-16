package ru.nsu.bookshop.controller;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import ru.nsu.bookshop.model.dto.BookLendingDTO;
import ru.nsu.bookshop.service.BookLendingService;
import ru.nsu.bookshop.service.BookService;
import ru.nsu.bookshop.service.ClientService;

@Controller
@RequestMapping("/lendings")
@AllArgsConstructor
public class BookLendingViewController {
    private final BookLendingService lendingService;
    private final BookService bookService;
    private final ClientService clientService;

    @GetMapping
    public String showLendings(@RequestParam(required = false) String clientName,
                            @RequestParam(required = false) String bookTitle,
                            @RequestParam(required = false) Boolean returnStatus,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Model model) {
        Pageable pageable = PageRequest.of(page, size);

        Page<BookLendingDTO> lendings = lendingService.searchLendings(
            (clientName == null || clientName.isEmpty()) ? null : clientName,
            (bookTitle == null || bookTitle.isEmpty()) ? null : bookTitle,
            returnStatus, pageable);

        model.addAttribute("lendings", lendings.getContent());
        model.addAttribute("currentPage", lendings.getNumber());
        model.addAttribute("totalPages", lendings.getTotalPages());
        model.addAttribute("totalItems", lendings.getTotalElements());
        model.addAttribute("clientName", clientName != null ? clientName : "");
        model.addAttribute("bookTitle", bookTitle != null ? bookTitle : "");
        model.addAttribute("returnStatus", returnStatus);

        return "lending/list";
    }

    @GetMapping("/new")
    public String showBorrowForm(Model model) {
        model.addAttribute("books", bookService.findAll());
        model.addAttribute("clients", clientService.findAll());
        return "lending/borrow-form";
    }

    @PostMapping("/borrow")
    public String borrowBook(@RequestParam Long clientId, 
                           @RequestParam Long bookId) throws NotFoundException {
        lendingService.borrowBook(clientId, bookId);
        return "redirect:/lendings";
    }

    @PostMapping("/{id}/return")
    public String returnBook(@PathVariable Long id) throws NotFoundException {
        lendingService.returnBook(id);
        return "redirect:/lendings";
    }
}