/* (C)2023 */
package com.springbootbooks.api.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class PhotoRequest {

    @Size(min = 2, max = 255, message = "Url must be between 2 and 255 characters")
    @NotNull(message = "key: 'url' should not be null")
    private String url;

    @Size(min = 2, max = 255, message = "Description name must be between 2 and 255 characters")
    @NotNull(message = "key: 'description' should not be null")
    private String description;
}
