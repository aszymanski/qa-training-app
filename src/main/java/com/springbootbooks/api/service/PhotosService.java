/* (C)2023 */
package com.springbootbooks.api.service;

import com.springbootbooks.api.dto.PhotoRequest;
import com.springbootbooks.api.entity.Photo;
import com.springbootbooks.api.exceptions.ObjectNotFoundException;
import com.springbootbooks.api.repository.PhotosRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PhotosService {

    private final PhotosRepository photosRepository;

    public PhotosService(PhotosRepository photosRepository) {
        this.photosRepository = photosRepository;
    }

    public void deletePhoto(Integer id) throws ObjectNotFoundException {
        boolean currentPhoto = photosRepository.existsById(id);
        if (currentPhoto) {
            photosRepository.deleteById(id);
        } else throw new ObjectNotFoundException("Photo with that id: " + id + " is not found");
    }

    public List<Photo> getAllPhotos() {
        return photosRepository.findAll();
    }

    public Photo savePhoto(PhotoRequest photoRequest) {
        Photo photo = Photo.create(0, photoRequest.getUrl(), photoRequest.getDescription());
        return photosRepository.save(photo);
    }

    public Photo updatePhoto(Integer id, PhotoRequest photoRequest) throws ObjectNotFoundException {
        if (id == null) {
            throw new ObjectNotFoundException("Book with that id: " + id + " is not found");
        }
        Optional<Photo> currentPhoto = photosRepository.findById(id);

        if (currentPhoto.isPresent()) {
            Photo photo = Photo.create(id, photoRequest.getUrl(), photoRequest.getDescription());
            return photosRepository.save(photo);
        } else throw new ObjectNotFoundException("Photo with that id: " + id + " is not found");
    }

    public Photo getPhoto(Integer id) throws ObjectNotFoundException {
        Optional<Photo> photo = photosRepository.findById(id);

        if (photo.isPresent()) {
            return photo.get();
        } else throw new ObjectNotFoundException("Photo with that id: " + id + " is not found");
    }
}
