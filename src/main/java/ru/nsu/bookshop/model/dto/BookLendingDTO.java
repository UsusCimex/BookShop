package ru.nsu.bookshop.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookLendingDTO {
    private Long id;
    private String clientFullName;
    private LocalDate clientBirthDate;
    private String bookTitle;
    private String bookAuthor;
    private String isbn;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
}
