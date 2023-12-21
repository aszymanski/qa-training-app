/* (C)2023 */
package com.springbootbooks.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.springbootbooks.api.controler.Views;
import com.springbootbooks.api.entity.Photo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookRequest {

    @Size(min = 2, max = 255, message = "Book name must be between 2 and 255 characters")
    @NotNull(message = "key: 'bookName' should not be null")
    @JsonView(Views.Intern.class)
    private String bookName;

    @Size(min = 2, max = 255, message = "Author name must be between 2 and 255 characters")
    @NotNull(message = "key: 'author' should not be null")
    @JsonView(Views.Intern.class)
    private String author;

    @JsonView(Views.Public.class)
    private Set<Photo> photos;
}
