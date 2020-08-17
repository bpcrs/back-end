package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.model.Brand;
import fpt.capstone.bpcrs.payload.BrandPayload;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BrandService {

    List<Brand> getAllBrand(int page, int size);

    Page<Brand> getAllBrandByAdmin(int page, int size);

    Brand getBrandById(int id);

    Brand createBrand(String name, String imageUrl);

    Brand updateBrand(int id, String name, String url);
}
