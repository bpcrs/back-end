package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.model.Model;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.ModelPayload;
import fpt.capstone.bpcrs.service.ModelService;
import fpt.capstone.bpcrs.util.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @RolesAllowed({"USER", "ADMINISTRATOR"})
    public ResponseEntity<?> getModels() {
        List<Model> models = modelService.getAll();
        List<ModelPayload.ResponseCreateModel> modelList = ObjectMapperUtils.mapAll(models, ModelPayload.ResponseCreateModel.class);
        return  ResponseEntity.ok(new ApiResponse<>(true, modelList));
    }

    @PostMapping
    @RolesAllowed("ADMINISTRATOR")
    public ResponseEntity<?> createModel(@Valid @RequestBody ModelPayload.RequestCreateModel request) {
        ModelPayload.ResponseCreateModel response = new ModelPayload.ResponseCreateModel();
        Model newModel = (Model) new Model().buildObject(request, true);
        modelService.createModel(newModel).buildObject(response, false);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getModel(@PathVariable() int id) {
        ModelPayload.ResponseCreateModel response = new ModelPayload.ResponseCreateModel();
        Model model = modelService.getModelById(id);
        if (model != null) {
            model.buildObject(response, false);
            return ResponseEntity.ok(new ApiResponse<>(true, response));
        }
        return new ResponseEntity(new ApiResponse<>(false, "Model with id= " + id + "not found"), HttpStatus.BAD_REQUEST);
    }

}
