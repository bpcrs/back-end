package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.model.Criteria;
import fpt.capstone.bpcrs.payload.ApiError;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.CriteriaPayload;
import fpt.capstone.bpcrs.service.CriteriaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/criteria")
@Slf4j
public class CriteriaController {

    @Autowired
    private CriteriaService criteriaService;

    @GetMapping
    public ResponseEntity<?> getAllCriteria(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "2") int size) {
        List<Criteria> criteriaList = criteriaService.getAllCriteria(page, size);
        return ResponseEntity.ok(new ApiResponse<>(true, criteriaList));
    }

    @PostMapping
    public ResponseEntity<?> createCriteria(@Valid @RequestBody CriteriaPayload.RequestCreateCriteria request) {
        criteriaService.createCriteria(request.buildCriteria());
        return ResponseEntity.ok(new ApiResponse<>(true, "create successful"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCriteria(@PathVariable() int id) {
        Criteria criteria = criteriaService.getCriteria(id);
        if (criteria != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, criteria));
        }
        return new ResponseEntity<>(new ApiError("id " + id + " not found", ""), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCriteria(@PathVariable() int id, @RequestBody CriteriaPayload.RequestCreateCriteria request) {
        Criteria criteria = criteriaService.updateCriteria(request.buildCriteria(), id);
        return ResponseEntity.ok(new ApiResponse<>(true, criteria));
    }
}
