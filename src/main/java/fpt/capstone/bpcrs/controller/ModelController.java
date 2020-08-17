package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.constant.RoleEnum;
import fpt.capstone.bpcrs.model.Brand;
import fpt.capstone.bpcrs.model.Model;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.BrandPayload;
import fpt.capstone.bpcrs.payload.ModelPayload;
import fpt.capstone.bpcrs.payload.PagingPayload;
import fpt.capstone.bpcrs.service.ModelService;
import fpt.capstone.bpcrs.util.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/model")
@Slf4j
public class ModelController {
    @Autowired
    private ModelService modelService;

    @GetMapping
    public ResponseEntity<?> getModels() {
        List<Model> models = modelService.getAll();
        List<ModelPayload.ResponseCreateModel> modelList = ObjectMapperUtils.mapAll(models, ModelPayload.ResponseCreateModel.class);
        return  ResponseEntity.ok(new ApiResponse<>(true, modelList));
    }

    @GetMapping("/admin")
    @RolesAllowed(RoleEnum.RoleType.ADMINISTRATOR)
    public ResponseEntity<?> getModelsByAdmin(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Model> models = modelService.getAllModelByAdmin(page, size);
        if (models.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(false, ""));
        }
        List<ModelPayload.ResponseModel> modelList = ObjectMapperUtils.mapAll(models.toList(), ModelPayload.ResponseModel.class);
        PagingPayload pagingPayload =
                PagingPayload.builder().data(modelList).count((int) models.getTotalElements()).build();
        return ResponseEntity.ok(new ApiResponse<>(true, pagingPayload));
    }

    @PutMapping("/{id}")
    @RolesAllowed(RoleEnum.RoleType.ADMINISTRATOR)
    public ResponseEntity<?> updateBrand(@PathVariable int id, @Valid @RequestParam String name) {
        ModelPayload.ResponseModel response = new ModelPayload.ResponseModel();
        modelService.updateModel(id, name).modelMaplerToObject(response, false);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }

    @PostMapping
    @RolesAllowed(RoleEnum.RoleType.ADMINISTRATOR)
    public ResponseEntity<?> createModel(@Valid @RequestParam String name) {
        ModelPayload.ResponseModel response = new ModelPayload.ResponseModel();
        modelService.createModel(name).modelMaplerToObject(response, false);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getModel(@PathVariable() int id) {
        ModelPayload.ResponseCreateModel response = new ModelPayload.ResponseCreateModel();
        Model model = modelService.getModelById(id);
        if (model != null) {
            model.modelMaplerToObject(response, false);
            return ResponseEntity.ok(new ApiResponse<>(true, response));
        }
        return new ResponseEntity(new ApiResponse<>(false, "Model with id= " + id + "not found"), HttpStatus.BAD_REQUEST);
    }

}
