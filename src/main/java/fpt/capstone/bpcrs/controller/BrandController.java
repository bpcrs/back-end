package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.model.Brand;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.BrandPayload;
import fpt.capstone.bpcrs.service.BrandService;
import fpt.capstone.bpcrs.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        return ResponseEntity.ok(new ApiResponse<>(true, brands));
    }

    @PostMapping
    public ResponseEntity<?> createBrand(@Valid @RequestBody BrandPayload.RequestCreateBrand request) {
        Brand brand = brandService.createBrand(request.buildBrand());
        return ResponseEntity.ok(new ApiResponse<>(true, brand));
    }
}
