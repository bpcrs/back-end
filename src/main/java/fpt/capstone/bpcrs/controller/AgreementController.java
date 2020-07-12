package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.constant.BookingEnum;
import fpt.capstone.bpcrs.constant.RoleEnum;
import fpt.capstone.bpcrs.model.Agreement;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.model.Criteria;
import fpt.capstone.bpcrs.model.Role;
import fpt.capstone.bpcrs.payload.AgreementPayload;
import fpt.capstone.bpcrs.payload.ApiError;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.BookingPayload;
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
import java.util.ArrayList;
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
    public ResponseEntity<?> createAgreementList(@PathVariable int bookingId) {
        Booking booking = bookingService.getBookingInformation(bookingId);
        if (booking == null) {
            return new ResponseEntity(new ApiError("Booking with id = " + bookingId + " not found", ""), HttpStatus.BAD_REQUEST);
        }
        List<Agreement> agreements = new ArrayList<>();
        List<Criteria> criteriaList = criteriaService.getAllCriteria();
        for (Criteria criteria : criteriaList) {
            Agreement agreement = Agreement.builder().booking(booking).criteria(criteria)
                    .isApproved(false).value(30).build();
            agreements.add(agreement);
        }
        List<Agreement> agreementList = agreementService.createAgreementList(agreements);
        List<AgreementPayload.ResponseCreateAgreement> responses = ObjectMapperUtils.mapAll(agreementList, AgreementPayload.ResponseCreateAgreement.class);
        return ResponseEntity.ok(new ApiResponse<>(true, responses));
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
    public ResponseEntity<?> updateAgreement(@PathVariable int bookingId ,@RequestBody List<AgreementPayload.RequestCreateAgreement> request) {
        Booking booking = bookingService.getBookingInformation(bookingId);
        List<Agreement> agreements = new ArrayList<>();
        for (AgreementPayload.RequestCreateAgreement agreement : request) {
        Criteria criteria = criteriaService.getCriteria(agreement.getCriteriaId());
            Agreement agre = Agreement.builder().booking(booking).criteria(criteria).isApproved(agreement.isApproved())
                    .value(agreement.getValue()).build();
            agreements.add(agre);
        }
        List<Agreement> agreementList = agreementService.updateAgreement(agreements);
        List<AgreementPayload.ResponseCreateAgreement> responses = ObjectMapperUtils.mapAll(agreementList, AgreementPayload.ResponseCreateAgreement.class);

        return ResponseEntity.ok(new ApiResponse<>(true, responses));
    }
}
