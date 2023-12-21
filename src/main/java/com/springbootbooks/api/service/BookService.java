/* (C)2023 */
package com.springbootbooks.api.service;

import static org.springframework.http.RequestEntity.patch;

import com.springbootbooks.api.adnotation.PatchAllowed;
import com.springbootbooks.api.controler.PatchController;
import com.springbootbooks.api.dto.BookRequest;
import com.springbootbooks.api.entity.Book;
import com.springbootbooks.api.entity.Photo;
import com.springbootbooks.api.exceptions.ObjectNotFoundException;
import com.springbootbooks.api.repository.BookRepository;
import com.springbootbooks.api.repository.PhotosRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.*;

@Service
@Transactional
public class BookService implements PatchController {

    private final BookRepository bookRepository;
    private final PhotosRepository photosRepository;

    public BookService(BookRepository bookRepository, PhotosRepository photosRepository) {
        this.bookRepository = bookRepository;
        this.photosRepository = photosRepository;
    }

    public Book saveBook(BookRequest bookRequest) {
        Set<Photo> photoList = validatePhotos(bookRequest.getPhotos());
        Book book = Book.create(0, bookRequest.getBookName(), bookRequest.getAuthor(), photoList);
        return bookRepository.save(book);
    }

    public Book addPhoto(Integer id, Photo photoRequest)
            throws ObjectNotFoundException, IllegalStateException {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) {
            Set<Photo> photoList = validatePhotos(Collections.singleton(photoRequest));
            if (photoList.stream().anyMatch(item -> book.get().getPhotos().contains(item))) {
                throw new IllegalStateException(
                        "Photo with that id: "
                                + photoRequest.getId()
                                + " already exist in that book: "
                                + book.get().getId()
                                + " ");
            }
            book.get().getPhotos().add(photoRequest);
            return bookRepository.save(book.get());
        } else throw new ObjectNotFoundException("Book with that id: " + id + " is not found");
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBook(Integer id) throws ObjectNotFoundException {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) {
            return book.get();
        } else throw new ObjectNotFoundException("Book with that id: " + id + " is not found");
    }

    public Book updateBook(Integer id, BookRequest bookRequest) {
        Set<Photo> photoList = validatePhotos(bookRequest.getPhotos());

        if (id == null) {
            throw new ObjectNotFoundException("Book with that id: " + id + " is not found");
        }
        Optional<Book> currentBook = bookRepository.findById(id);

        if (currentBook.isPresent()) {
            Book book =
                    Book.create(id, bookRequest.getBookName(), bookRequest.getAuthor(), photoList);
            return bookRepository.save(book);
        } else throw new ObjectNotFoundException("Book with that id: " + id + " is not found");
    }

    public void deleteBook(Integer id) throws ObjectNotFoundException {
        boolean currentBook = bookRepository.existsById(id);
        if (currentBook) {
            bookRepository.deleteById(id);
        } else throw new ObjectNotFoundException("Book with that id: " + id + " is not found");
    }

    public Book updateFieldsByBook(Integer id, BookRequest bookRequest)
            throws ObjectNotFoundException {
        validatePhotos(bookRequest.getPhotos());

        return bookRepository
                .findById(id)
                .map(
                        Book -> {
                            try {
                                Book = patch(Book, bookRequest);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            return bookRepository.save(Book);
                        })
                .orElseThrow(
                        () ->
                                new ObjectNotFoundException(
                                        "Book with that id: " + id + " is not found"));
    }

    private static void validateKeyFields(BookRequest bookRequest) throws ObjectNotFoundException {
        String tmpKey = null;
        try {
            for (Field field : bookRequest.getClass().getDeclaredFields()) {
                tmpKey = field.getName();
                Objects.requireNonNull(field);
                if (field.get(bookRequest) != null
                        && !field.getAnnotation(PatchAllowed.class).isPatchAllowed()) {
                    throw new ObjectNotFoundException("Field cannot be patched: " + tmpKey);
                }
            }
        } catch (NullPointerException | IllegalAccessException exception) {
            throw new ObjectNotFoundException("Cannot find patch key: " + tmpKey);
        }
    }

    private Set<Photo> validatePhotos(Set<Photo> photos) throws ObjectNotFoundException {
        Set<Photo> photoList = new HashSet<>();

        if (photos == null) {
            return photoList;
        }
        for (Photo item : photos) {
            if (item.getId() == null) {
                Photo photo = Photo.create(0, item.getUrl(), item.getDescription());
                photoList.add(photo);
            } else if (photosRepository.findAll().contains(item)) {
                photoList.add(item);
            } else
                throw new ObjectNotFoundException(
                        "Photo with that id: "
                                + item.getId()
                                + " is not found or cannot by modified.");
        }
        return photoList;
    }
}
