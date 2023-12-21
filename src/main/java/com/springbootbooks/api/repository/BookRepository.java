/* (C)2023 */
package com.springbootbooks.api.repository;

import com.springbootbooks.api.entity.Book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Book save(Book book);

    Optional<Book> findById(Integer id);
}
