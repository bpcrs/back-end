package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.model.Brand;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BrandService {

    List<Brand> getAllBrand(int page, int size);

    Page<Brand> getAllBrandByAdmin(int page, int size);

    Brand getBrandById(int id);

    Brand createBrand(Brand brand);
}
