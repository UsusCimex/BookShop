package ru.nsu.bookshop.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ru.nsu.bookshop.model.dto.BookLendingDTO;
import ru.nsu.bookshop.model.entity.Book;
import ru.nsu.bookshop.model.entity.BookLending;
import ru.nsu.bookshop.model.entity.Client;
import ru.nsu.bookshop.repositrory.BookLendingRepository;

@Service
@AllArgsConstructor
public class BookLendingService {
    private final BookLendingRepository lendingRepository;
    private final ClientService clientService;
    private final BookService bookService;

    public Page<BookLendingDTO> searchLendings(String clientName, String bookTitle, Boolean returnStatus, Pageable pageable) {
        return lendingRepository.searchLendings(clientName, bookTitle, returnStatus, pageable)
                .map(this::convertToDTO);
    }

    public List<BookLendingDTO> findAll() {
        return lendingRepository.findAll()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public BookLending borrowBook(Long clientId, Long bookId) throws NotFoundException {
        Client client = clientService.findById(clientId)
            .orElseThrow(() -> new NotFoundException());
        Book book = bookService.findById(bookId)
            .orElseThrow(() -> new NotFoundException());

        BookLending lending = new BookLending();
        lending.setClient(client);
        lending.setBook(book);
        lending.setBorrowDate(LocalDateTime.now());

        return lendingRepository.save(lending);
    }

    public BookLending returnBook(Long lendingId) throws NotFoundException {
        BookLending lending = lendingRepository.findById(lendingId)
            .orElseThrow(() -> new NotFoundException());
        lending.setReturnDate(LocalDateTime.now());
        return lendingRepository.save(lending);
    }

    public List<BookLendingDTO> getAllActiveLendings() {
        return lendingRepository.findByReturnDateIsNull()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    private BookLendingDTO convertToDTO(BookLending lending) {
        return new BookLendingDTO(
            lending.getId(),
            lending.getClient().getFirstName() + " " + lending.getClient().getLastName(),
            lending.getClient().getBirthDate(),
            lending.getBook().getTitle(),
            lending.getBook().getAuthor(),
            lending.getBook().getIsbn(),
            lending.getBorrowDate(),
            lending.getReturnDate()
        );
    }
}