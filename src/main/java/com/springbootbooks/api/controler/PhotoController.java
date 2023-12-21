/* (C)2023 */
package com.springbootbooks.api.controler;

import com.springbootbooks.api.dto.PhotoRequest;
import com.springbootbooks.api.entity.Photo;
import com.springbootbooks.api.exceptions.ObjectNotFoundException;
import com.springbootbooks.api.service.PhotosService;

import jakarta.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/photo")
public class PhotoController {

    private final PhotosService photoService;

    public PhotoController(PhotosService photoService) {
        this.photoService = photoService;
    }

    @PostMapping()
    public ResponseEntity<Photo> savePhoto(@RequestBody @Valid PhotoRequest photoRequest) {
        return new ResponseEntity<>(photoService.savePhoto(photoRequest), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Photo>> getAll() {
        return ResponseEntity.ok(photoService.getAllPhotos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Photo> updatePhoto(
            @PathVariable Integer id, @RequestBody @Valid PhotoRequest photoRequest)
            throws ObjectNotFoundException {
        return ResponseEntity.ok(photoService.updatePhoto(id, photoRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Photo> getPhoto(@PathVariable Integer id) throws ObjectNotFoundException {
        return ResponseEntity.ok(photoService.getPhoto(id));
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Integer id) throws DataIntegrityViolationException {
        try {
            photoService.deletePhoto(id);
        } catch (DataIntegrityViolationException exception) {
            throw new DataIntegrityViolationException(
                    "Photo cannot be deleted because its in use.");
        }
    }
}
