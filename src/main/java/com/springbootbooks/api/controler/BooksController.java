/* (C)2023 */
package com.springbootbooks.api.controler;

import com.springbootbooks.api.entity.Book;
import com.springbootbooks.api.service.BookService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;

    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }
}
