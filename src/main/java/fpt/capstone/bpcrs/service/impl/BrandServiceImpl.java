package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.model.Brand;
import fpt.capstone.bpcrs.repository.BrandRepository;
import fpt.capstone.bpcrs.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Brand getBrandById(int id) {
        return brandRepository.findById(id).orElse(null);
    }
}
