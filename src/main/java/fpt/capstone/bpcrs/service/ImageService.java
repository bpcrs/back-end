package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.model.Image;

import java.util.List;

public interface ImageService {
    List<Image> getAllImagePaging(int page, int size, int carId);

    List<Image> createImages(List<Image> images);
}
