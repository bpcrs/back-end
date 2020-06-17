package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.component.Paging;
import fpt.capstone.bpcrs.model.Brand;
import fpt.capstone.bpcrs.repository.BrandRepository;
import fpt.capstone.bpcrs.service.BrandService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Brand> getAllBrand(int page, int size) {
        Page<Brand> brands = brandRepository.findAll(new Paging(page, size, Sort.unsorted()));
        return brands.get().collect(Collectors.toList());
    }

    @Override
    public Brand getBrandById(int id) {
        return brandRepository.findById(id).orElse(null);
    }

    @Override
    public Brand createBrand(Brand brand) {
        return brandRepository.save(brand);
    }
}
