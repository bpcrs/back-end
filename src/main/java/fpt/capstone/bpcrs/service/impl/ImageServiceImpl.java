package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.component.Paging;
import fpt.capstone.bpcrs.constant.ImageTypeEnum;
import fpt.capstone.bpcrs.model.Image;
import fpt.capstone.bpcrs.repository.ImageRepository;
import fpt.capstone.bpcrs.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Page<Image> getAllImagePaging(int page, int size, int carId, ImageTypeEnum type) {
        return imageRepository.findAllByCar_IdAndType(carId, type, new Paging(page, size, Sort.unsorted()));
    }

    @Override
    public List<Image> getAllImage(int carId, ImageTypeEnum type) {
        return imageRepository.findAllByCar_IdAndType(carId, type);
    }

    @Override
    public List<Image> createImages(List<Image> images) {
        return imageRepository.saveAll(images);
    }
}
