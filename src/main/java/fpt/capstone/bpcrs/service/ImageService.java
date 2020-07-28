package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.constant.ImageTypeEnum;
import fpt.capstone.bpcrs.model.Image;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ImageService {
    Page<Image> getAllImagePaging(int page, int size, int carId, ImageTypeEnum type);
    List<Image> getAllImage(int carId, ImageTypeEnum type);
    List<Image> createImages(List<Image> images);
}
