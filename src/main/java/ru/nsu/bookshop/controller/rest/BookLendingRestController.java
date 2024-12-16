package ru.nsu.bookshop.controller.rest;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.nsu.bookshop.model.dto.BookLendingDTO;
import ru.nsu.bookshop.service.BookLendingService;

@RestController
@RequestMapping("/api/lendings")
@AllArgsConstructor
public class BookLendingRestController {
    private final BookLendingService lendingService;

    @GetMapping
    public Page<BookLendingDTO> getLendings(
            @RequestParam(required = false) String clientName,
            @RequestParam(required = false) String bookTitle,
            @RequestParam(required = false) Boolean returnStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return lendingService.searchLendings(
                (clientName == null || clientName.isEmpty()) ? null : clientName,
                (bookTitle == null || bookTitle.isEmpty()) ? null : bookTitle,
                returnStatus,
                pageable
        );
    }

    @GetMapping("/all")
    public List<BookLendingDTO> findAll() {
        return lendingService.findAll();
    }

    @PostMapping("/borrow")
    public void borrowBook(@RequestParam Long clientId, @RequestParam Long bookId) throws Exception {
        lendingService.borrowBook(clientId, bookId);
    }

    @PostMapping("/{id}/return")
    public void returnBook(@PathVariable Long id) throws Exception {
        lendingService.returnBook(id);
    }
}
