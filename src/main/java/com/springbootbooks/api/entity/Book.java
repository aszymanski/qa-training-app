/* (C)2023 */
package com.springbootbooks.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "BOOKS_TBL")
@Data
@AllArgsConstructor(staticName = "create")
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "bookName")
    @Size(min = 2, max = 255, message = "Book name must be between 2 and 255 characters")
    @NotNull(message = "key: 'bookName' should not be null")
    private String bookName;

    @Column(name = "author")
    @Size(min = 2, max = 255, message = "Author name must be between 2 and 255 characters")
    @NotNull(message = "key: 'author' should not be null")
    private String author;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "BOOKS_TBL_PHOTOS_TBL",
            joinColumns = @JoinColumn(name = "BOOKS_TBL_id"),
            inverseJoinColumns = @JoinColumn(name = "PHOTOS_TBL_id"))
    private Set<Photo> photos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String name) {
        this.bookName = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
