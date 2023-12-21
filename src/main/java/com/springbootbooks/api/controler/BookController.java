/* (C)2023 */
package com.springbootbooks.api.controler;

import com.springbootbooks.api.dto.BookRequest;
import com.springbootbooks.api.entity.Book;
import com.springbootbooks.api.entity.Photo;
import com.springbootbooks.api.exceptions.ObjectNotFoundException;
import com.springbootbooks.api.service.BookService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody @Valid BookRequest bookRequest) {
        return new ResponseEntity<>(bookService.saveBook(bookRequest), HttpStatus.CREATED);
    }

    @PostMapping("/{bookId}/photo")
    public ResponseEntity<Book> addPhotoToBook(
            @PathVariable Integer bookId, @RequestBody @Valid Photo photo) {
        return new ResponseEntity<>(bookService.addPhoto(bookId, photo), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Integer id) throws ObjectNotFoundException {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Integer id, @RequestBody @Valid BookRequest bookRequest)
            throws ObjectNotFoundException {
        return ResponseEntity.ok(bookService.updateBook(id, bookRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Book> updateFieldsBook(
            @PathVariable Integer id, @RequestBody BookRequest bookRequest)
            throws ObjectNotFoundException {
        return ResponseEntity.ok(bookService.updateFieldsByBook(id, bookRequest));
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Integer id) throws ObjectNotFoundException {
        bookService.deleteBook(id);
    }
}
