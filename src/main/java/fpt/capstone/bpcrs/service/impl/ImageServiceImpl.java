package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.component.Paging;
import fpt.capstone.bpcrs.model.Image;
import fpt.capstone.bpcrs.repository.ImageRepository;
import fpt.capstone.bpcrs.service.ImageService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ImageServiceImpl  implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<Image> getAllImagePaging(int page, int size, int carId) {
        Page<Image> images = imageRepository.findAllByCar_Id(carId, new Paging(page, size, Sort.unsorted()));
        return images.get().collect(Collectors.toList());
    }

    @Override
    public Image createImage(Image newImage) {
        return imageRepository.save(newImage);
    }
}
