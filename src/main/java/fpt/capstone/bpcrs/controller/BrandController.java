package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.model.Brand;
import fpt.capstone.bpcrs.model.Car;
import fpt.capstone.bpcrs.model.Review;
import fpt.capstone.bpcrs.payload.ApiError;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.BrandPayload;
import fpt.capstone.bpcrs.payload.ReviewPayload;
import fpt.capstone.bpcrs.service.BrandService;
import fpt.capstone.bpcrs.service.CarService;
import fpt.capstone.bpcrs.util.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/brand")
@Slf4j
public class BrandController {

    @Autowired
    private CarService carService;

    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<?> getBrands(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        List<Brand> brands = brandService.getAllBrand(page, size);
        List<BrandPayload.ResponseCreateBrand> brandList = ObjectMapperUtils.mapAll(brands,BrandPayload.ResponseCreateBrand.class);
        return ResponseEntity.ok(new ApiResponse<>(true, brandList));
    }

    @PostMapping
    public ResponseEntity<?> createBrand(@Valid @RequestBody BrandPayload.RequestCreateBrand request) {
        BrandPayload.ResponseCreateBrand response = new BrandPayload.ResponseCreateBrand();
        Brand newBrand = (Brand) new Brand().buildObject(request, true);
        brandService.createBrand(newBrand).buildObject(response, false);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }
}
