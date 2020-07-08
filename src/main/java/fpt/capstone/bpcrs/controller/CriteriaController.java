package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.constant.RoleEnum;
import fpt.capstone.bpcrs.model.Criteria;
import fpt.capstone.bpcrs.payload.ApiError;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.CriteriaPayload;
import fpt.capstone.bpcrs.service.CriteriaService;
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
@RequestMapping("/criteria")
@Slf4j
public class CriteriaController {

    @Autowired
    private CriteriaService criteriaService;

    @GetMapping
    @RolesAllowed({RoleEnum.RoleType.USER, RoleEnum.RoleType.ADMINISTRATOR})
    public ResponseEntity<?> getAllCriteria(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "2") int size) {
        List<Criteria> criteriaList = criteriaService.getAllCriteria(page, size);
        if (criteriaList.isEmpty()) {
            return new ResponseEntity(new ApiResponse<>(false, "Dont have any criteria"), HttpStatus.BAD_REQUEST);
        }
        List<CriteriaPayload.ResposneCreateCriteria> resposneList = ObjectMapperUtils.mapAll(criteriaList, CriteriaPayload.ResposneCreateCriteria.class);
        return ResponseEntity.ok(new ApiResponse<>(true, resposneList));
    }

    @PostMapping
    @RolesAllowed({RoleEnum.RoleType.USER, RoleEnum.RoleType.ADMINISTRATOR})
    public ResponseEntity<?> createCriteria(@Valid @RequestBody CriteriaPayload.RequestCreateCriteria request) {
        CriteriaPayload.ResposneCreateCriteria response = new CriteriaPayload.ResposneCreateCriteria();
        Criteria criteria = (Criteria) new Criteria().buildObject(request, true);
        criteriaService.createCriteria(criteria).buildObject(response, false);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }

    @GetMapping("/{id}")
    @RolesAllowed({RoleEnum.RoleType.USER, RoleEnum.RoleType.ADMINISTRATOR})
    public ResponseEntity<?> getCriteria(@PathVariable() int id) {
        Criteria criteria = criteriaService.getCriteria(id);
        if (criteria != null) {
            CriteriaPayload.ResposneCreateCriteria cri = ObjectMapperUtils.map(criteria, CriteriaPayload.ResposneCreateCriteria.class);
            return ResponseEntity.ok(new ApiResponse<>(true, cri));
        }
        return new ResponseEntity<>(new ApiError("id " + id + " not found", ""), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    @RolesAllowed({RoleEnum.RoleType.USER, RoleEnum.RoleType.ADMINISTRATOR})
    public ResponseEntity<?> updateCriteria(@PathVariable() int id, @RequestBody CriteriaPayload.RequestCreateCriteria request) {
        CriteriaPayload.ResposneCreateCriteria response =  new CriteriaPayload.ResposneCreateCriteria();
        Criteria criteria = (Criteria) new Criteria().buildObject(request, true);
                criteriaService.updateCriteria(criteria, id).buildObject(response, false);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }
}
