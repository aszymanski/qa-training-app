/* (C)2023 */
package com.springbootbooks.api.repository;

import com.springbootbooks.api.entity.Photo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhotosRepository extends JpaRepository<Photo, Integer> {
    Photo save(Photo photo);

    Optional<Photo> findById(Integer id);
}
