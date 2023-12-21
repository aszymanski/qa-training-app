/* (C)2023 */
package com.springbootbooks.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PHOTOS_TBL")
@Data
@AllArgsConstructor(staticName = "create")
@NoArgsConstructor
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "url")
    @Size(min = 2, max = 255, message = "Url must be between 2 and 255 characters")
    @NotNull(message = "key: 'url' should not be null")
    private String url;

    @Column(name = "description")
    @Size(min = 2, max = 255, message = "Description name must be between 2 and 255 characters")
    @NotNull(message = "key: 'description' should not be null")
    private String description;
}
