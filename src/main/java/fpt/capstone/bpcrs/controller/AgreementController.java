package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.constant.RoleEnum;
import fpt.capstone.bpcrs.model.Agreement;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.model.Criteria;
import fpt.capstone.bpcrs.model.Role;
import fpt.capstone.bpcrs.payload.AgreementPayload;
import fpt.capstone.bpcrs.payload.ApiError;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.service.AgreementService;
import fpt.capstone.bpcrs.service.BookingService;
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
@RequestMapping("/agreement")
@Slf4j
public class AgreementController {
    @Autowired
    private AgreementService agreementService;
    @Autowired
    private CriteriaService criteriaService;
    @Autowired
    private BookingService bookingService;

    @PostMapping
    @RolesAllowed(RoleEnum.RoleType.USER)
    public ResponseEntity<?> createAgreement( @Valid @RequestBody AgreementPayload.RequestCreateAgreement request) {
        Booking booking = bookingService.getBookingInformation(request.getBookingId());
        if (booking == null) {
            return new ResponseEntity(new ApiError("Booking with id = " + request.getBookingId() + " not found", ""), HttpStatus.BAD_REQUEST);
        }
        Criteria criteria = criteriaService.getCriteria(request.getCriteriaId());
        if (criteria == null) {
            return new ResponseEntity(new ApiError("Criteria with id = " + request.getCriteriaId() + " not found", ""), HttpStatus.BAD_REQUEST);
        }
        AgreementPayload.ResponseCreateAgreement response = new AgreementPayload.ResponseCreateAgreement();
        Agreement agreement = (Agreement) new Agreement().buildObject(request, true);
        agreement.setBooking(booking);
        agreement.setCriteria(criteria);
        agreementService.createAgreement(agreement).buildObject(response, false);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }

    @GetMapping("/get/{id}")
    @RolesAllowed({RoleEnum.RoleType.USER, RoleEnum.RoleType.ADMINISTRATOR})
    public ResponseEntity<?> getAgreementsByBookingId(@PathVariable int id) {
        List<Agreement> agreements = agreementService.getListAgreementByBookingID(id);
        if (agreements.isEmpty()) {
            return new ResponseEntity(new ApiResponse<>(false, "Dont have any agreement with booking_id =" + id), HttpStatus.BAD_REQUEST);
        }
        List<AgreementPayload.ResponseCreateAgreement> agreementList = ObjectMapperUtils.mapAll(agreements, AgreementPayload.ResponseCreateAgreement.class);
        return ResponseEntity.ok(new ApiResponse<>(true, agreementList));
    }

    @GetMapping()
    @RolesAllowed({RoleEnum.RoleType.USER, RoleEnum.RoleType.ADMINISTRATOR})
    public ResponseEntity<?> getAgreementById(@RequestParam int id) {
        Agreement agreement = agreementService.getAgreementById(id);
        if (agreement == null) {
            return new ResponseEntity(new ApiResponse<>(false, "Dont have any agreement with id " + id), HttpStatus.BAD_REQUEST);
        }
        AgreementPayload.ResponseCreateAgreement agr = ObjectMapperUtils.map(agreement, AgreementPayload.ResponseCreateAgreement.class);
        return ResponseEntity.ok(new ApiResponse<>(true, agr));
    }

    @PutMapping("/{id}")
    @RolesAllowed(RoleEnum.RoleType.USER)
    public ResponseEntity<?> updateAgreement(@PathVariable() int id, @RequestBody AgreementPayload.RequestCreateAgreement request) {
        boolean isExisted = agreementService.getAgreementById(id) != null;
        if (!isExisted) {
            return new ResponseEntity(new ApiError("Agreement with id = " + id + " not found", ""), HttpStatus.BAD_REQUEST);
        }

        AgreementPayload.ResponseCreateAgreement response = new AgreementPayload.ResponseCreateAgreement();
        Agreement update = (Agreement) new Agreement().buildObject(request, true);
        agreementService.updateAgreement(id, update).buildObject(response, false);
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }
}
