/* (C)2023 */
package com.springbootbooks.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.springbootbooks.api.dto.BookRequest;
import com.springbootbooks.api.dto.PhotoRequest;
import com.springbootbooks.api.entity.Book;
import com.springbootbooks.api.entity.Photo;
import com.springbootbooks.api.exceptions.ObjectNotFoundException;
import com.springbootbooks.api.repository.BookRepository;
import com.springbootbooks.api.repository.PhotosRepository;
import com.springbootbooks.api.service.BookService;
import com.springbootbooks.api.service.PhotosService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@SpringBootTest(classes = SpringBootBooksApplication.class)
@AutoConfigureMockMvc
class SpringBootBooksApplicationTests {

    @Mock private PhotosRepository photosRepository;
    @Mock private BookRepository bookRepository;
    @InjectMocks private PhotosService photosService;
    @InjectMocks private BookService bookService;

    Photo photo = Photo.create(1, "photo_url", "description");
    PhotoRequest photoRequest = PhotoRequest.build( "photo_url", "description");

    Book book = Book.create(0, "book_name", "book_author", Collections.singleton(photo));

    @Test
    void Should_PhotoServiceSavePhoto_When_SendRequest() {

        when(photosRepository.save(any(Photo.class))).thenReturn(photo);

        Photo photoSaved = photosService.savePhoto(new PhotoRequest());

        assertEquals(photo, photoSaved);
        Mockito.verify(photosRepository, times(1)).save(any(Photo.class));
        verifyNoMoreInteractions(photosRepository);
    }

    @Test
    void Should_BookServiceSaveBook_When_SendRequest() {

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book bookSaved = bookService.saveBook(new BookRequest());

        assertEquals(book, bookSaved);
        Mockito.verify(bookRepository, times(1)).save(any(Book.class));
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void Should_PhotoServiceGetOnePhoto_When_SendRequest() {

        when(photosRepository.findById(anyInt())).thenReturn(Optional.of(photo));

        Photo photoSaved = photosService.getPhoto(getRandomInt());

        assertEquals(photo, photoSaved);
        Mockito.verify(photosRepository, times(1)).findById(anyInt());
        verifyNoMoreInteractions(photosRepository);
    }

    @Test
    void Should_BookServicGetOneBook_When_SendRequest() {

        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));

        Book bookSaved = bookService.getBook(getRandomInt());

        assertEquals(book, bookSaved);
        Mockito.verify(bookRepository, times(1)).findById(anyInt());
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void Should_Not_PhotoServiceGetOnePhoto_When_SendRequest() {

        when(photosRepository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(
                ObjectNotFoundException.class, () -> photosService.getPhoto(getRandomInt()));
        Mockito.verify(photosRepository, times(1)).findById(anyInt());
        verifyNoMoreInteractions(photosRepository);
    }

    @Test
    void Should_Not_BookServiceGetOneBook_When_SendRequest() {

        when(bookRepository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(
                ObjectNotFoundException.class, () -> bookService.getBook(getRandomInt()));
        Mockito.verify(bookRepository, times(1)).findById(anyInt());
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void Should_PhotoServiceDeleteOnePhoto_When_SendRequest() {

        when(photosRepository.existsById(anyInt())).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> photosService.deletePhoto(getRandomInt()));
        Mockito.verify(photosRepository, times(1)).existsById(anyInt());
        Mockito.verify(photosRepository, times(1)).deleteById(anyInt());
        verifyNoMoreInteractions(photosRepository);
    }

    @Test
    void Should_BookServiceDeleteOneBook_When_SendRequest() {

        when(bookRepository.existsById(anyInt())).thenReturn(true);
        doNothing().when(bookRepository).deleteById(anyInt());

        Assertions.assertDoesNotThrow(() -> bookService.deleteBook(getRandomInt()));
        Mockito.verify(bookRepository, times(1)).existsById(anyInt());
        Mockito.verify(bookRepository, times(1)).deleteById(anyInt());
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void Should_Not_PhotoServiceDeleteOnePhoto_When_SendRequest() {

        when(photosRepository.existsById(anyInt())).thenReturn(false);

        Assertions.assertThrows(
                ObjectNotFoundException.class, () -> photosService.deletePhoto(getRandomInt()));
        Mockito.verify(photosRepository, times(1)).existsById(anyInt());
        verifyNoMoreInteractions(photosRepository);
    }

    @Test
    void Should_Not_BookServiceDeleteOneBook_When_SendRequest() {

        when(bookRepository.existsById(anyInt())).thenReturn(false);

        Assertions.assertThrows(
                ObjectNotFoundException.class, () -> bookService.deleteBook(getRandomInt()));
        Mockito.verify(bookRepository, times(1)).existsById(anyInt());
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void Should_BookServiceGetAllBook_When_SendRequest() {

        when(bookRepository.findAll()).thenReturn(List.of(new Book(), new Book()));

        assertEquals(2, bookService.getAllBooks().size());
        Mockito.verify(bookRepository, times(1)).findAll();
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void Should_PhotoServiceGetAllPhoto_When_SendRequest() {

        when(photosRepository.findAll()).thenReturn(List.of(new Photo(), new Photo()));

        assertEquals(2, photosService.getAllPhotos().size());
        Mockito.verify(photosRepository, times(1)).findAll();
        verifyNoMoreInteractions(photosRepository);
    }

    @Test
    void Should_Not_PhotoServiceUpdatePhoto_When_SendRequestIdNull() {

        when(photosRepository.findById(anyInt())).thenReturn(Optional.of(new Photo()));

        Assertions.assertThrows(
                ObjectNotFoundException.class, () -> photosService.updatePhoto(null, new PhotoRequest()));
        Mockito.verify(photosRepository, times(0)).findById(anyInt());
        Mockito.verify(photosRepository, times(0)).save(new Photo());
        verifyNoMoreInteractions(photosRepository);
    }

    @Test
    void Should_Not_PhotoServiceUpdatePhoto_When_SendRequest() {

        when(photosRepository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(
                ObjectNotFoundException.class, () -> photosService.updatePhoto(anyInt(), new PhotoRequest()));
        Mockito.verify(photosRepository, times(1)).findById(anyInt());
        Mockito.verify(photosRepository, times(0)).save(new Photo());

        verifyNoMoreInteractions(photosRepository);
    }
    @Test
    void Should_PhotoServiceUpdatePhoto_When_SendRequest() {

        when(photosRepository.findById(anyInt())).thenReturn(Optional.of(photo));
        when(photosRepository.save(photo)).thenReturn(photo);

        Photo photoUpdated = photosService.updatePhoto(photo.getId(),PhotoRequest.build(photo.getUrl(),photo.getDescription()));

        assertEquals(photo, photoUpdated);

        Mockito.verify(photosRepository, times(1)).findById(anyInt());
        Mockito.verify(photosRepository, times(1)).save(photo);

        verifyNoMoreInteractions(photosRepository);
    }

    private int getRandomInt() {
        return new Random().ints(1, 10).findFirst().getAsInt();
    }
}
