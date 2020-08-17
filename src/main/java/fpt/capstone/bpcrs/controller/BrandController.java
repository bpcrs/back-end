package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.constant.RoleEnum;
import fpt.capstone.bpcrs.model.Brand;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.BrandPayload;
import fpt.capstone.bpcrs.payload.PagingPayload;
import fpt.capstone.bpcrs.service.BrandService;
import fpt.capstone.bpcrs.util.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/brand")
@Slf4j
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<?> getBrands(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        List<Brand> brands = brandService.getAllBrand(page, size);
        List<BrandPayload.ResponseBrand> brandList = ObjectMapperUtils.mapAll(brands, BrandPayload.ResponseBrand.class);
        return ResponseEntity.ok(new ApiResponse<>(true, brandList));
    }

    @GetMapping("/admin")
    @RolesAllowed(RoleEnum.RoleType.ADMINISTRATOR)
    public ResponseEntity<?> getBrandsByAdmin(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Brand> brands = brandService.getAllBrandByAdmin(page, size);
        if (brands.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(false, ""));
        }
        List<BrandPayload.ResponseBrand> brandList = ObjectMapperUtils.mapAll(brands.toList(), BrandPayload.ResponseBrand.class);
        PagingPayload pagingPayload =
                PagingPayload.builder().data(brandList).count((int) brands.getTotalElements()).build();
        return ResponseEntity.ok(new ApiResponse<>(true, pagingPayload));
    }

    @PutMapping("/{id}")
    @RolesAllowed(RoleEnum.RoleType.ADMINISTRATOR)
    public ResponseEntity<?> updateBrand(@PathVariable int id, @Valid @RequestParam String name, @Valid @RequestParam String imageUrl) {
        BrandPayload.ResponseBrand response = new BrandPayload.ResponseBrand();
        brandService.updateBrand(id, name, imageUrl).modelMaplerToObject(response, false);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }

    @PostMapping
    @RolesAllowed(RoleEnum.RoleType.ADMINISTRATOR)
    public ResponseEntity<?> createBrand(@Valid @RequestParam String name, @Valid @RequestParam String imageUrl) {
        BrandPayload.ResponseBrand response = new BrandPayload.ResponseBrand();
        brandService.createBrand(name, imageUrl).modelMaplerToObject(response, false);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }
}
