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
        List<BrandPayload.ResponseCreateBrand> brandList = ObjectMapperUtils.mapAll(brands, BrandPayload.ResponseCreateBrand.class);
        return ResponseEntity.ok(new ApiResponse<>(true, brandList));
    }

    @GetMapping("/admin")
    @RolesAllowed(RoleEnum.RoleType.ADMINISTRATOR)
    public ResponseEntity<?> getBrandsByAdmin(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Brand> brands = brandService.getAllBrandByAdmin(page, size);
        if (brands.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(false, ""));
        }
        List<BrandPayload.ResponseCreateBrand> brandList = ObjectMapperUtils.mapAll(brands.toList(), BrandPayload.ResponseCreateBrand.class);
        PagingPayload pagingPayload =
                PagingPayload.builder().data(brandList).count((int) brands.getTotalElements()).build();
        return ResponseEntity.ok(new ApiResponse<>(true, pagingPayload));
    }

    @PutMapping("/update")
    @RolesAllowed(RoleEnum.RoleType.ADMINISTRATOR)
    public ResponseEntity<?> updateBrand(@Valid @RequestBody BrandPayload.RequestUpdateBrand request) {
        BrandPayload.RequestUpdateBrand response = new BrandPayload.RequestUpdateBrand();
        brandService.updateBrand(request).modelMaplerToObject(response, false);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }


    @PostMapping
    @RolesAllowed(RoleEnum.RoleType.ADMINISTRATOR)
    public ResponseEntity<?> createBrand(@Valid @RequestBody BrandPayload.RequestCreateBrand request) {
        BrandPayload.ResponseCreateBrand response = new BrandPayload.ResponseCreateBrand();
        Brand newBrand = (Brand) new Brand().modelMaplerToObject(request, true);
        brandService.createBrand(newBrand).modelMaplerToObject(response, false);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }
}
