package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.constant.RoleEnum;
import fpt.capstone.bpcrs.exception.BpcrsException;
import fpt.capstone.bpcrs.model.*;
import fpt.capstone.bpcrs.payload.AgreementPayload;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.service.AccountService;
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
    @Autowired
    private AccountService accountService;

//    @PostMapping
//    @RolesAllowed(RoleEnum.RoleType.USER)
//    public ResponseEntity<?> createAgreementList(@Valid @RequestBody List<AgreementPayload.RequestCreateAgreement> requests) {
//        List<Agreement> agreements = new ArrayList<>();
//        for (AgreementPayload.RequestCreateAgreement agreement : requests) {
//            Booking booking = bookingService.getBookingInformation(agreement.getBookingId());
//            Criteria criteria = criteriaService.getCriteria(agreement.getCriteriaId());
//            Agreement newAgreement = Agreement.builder().booking(booking).criteria(criteria)
//                    .isApproved(agreement.isApproved()).value(agreement.getValue()).name(agreement.getName()).build();
//            agreements.add(newAgreement);
//        }
//        List<Agreement> agreementList = agreementService.createAgreementList(agreements);
//        List<AgreementPayload.ResponseCreateAgreement> responses = ObjectMapperUtils.mapAll(agreementList, AgreementPayload.ResponseCreateAgreement.class);
//        return ResponseEntity.ok(new ApiResponse<>(true, responses));
//    }

    @GetMapping("/booking/{id}")
    @RolesAllowed({RoleEnum.RoleType.USER})
    public ResponseEntity<?> getAgreementsByBookingId(@PathVariable int id) {
        Booking booking = bookingService.getBookingInformation(id);
        if (booking == null){
            return new ResponseEntity(new ApiResponse<>(false, "Dont have any booking id =" + id), HttpStatus.BAD_REQUEST);
        }
        boolean isRenter = booking.getRenter().getId().intValue() == accountService.getCurrentUser().getId().intValue();
        List<Agreement> agreements = agreementService.getListAgreementByBookingID(id, isRenter);
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

    @PutMapping()
    @RolesAllowed({RoleEnum.RoleType.USER})
    public ResponseEntity<?> acceptAgreement(@RequestParam int criteriaId, @RequestParam int bookingId) {
        if (criteriaService.getCriteria(criteriaId) == null || bookingService.getBookingInformation(bookingId) == null) {
            return new ResponseEntity(new ApiResponse<>(false, "Dont have any booking with id=" + bookingId + ", and criteria id=" + criteriaId), HttpStatus.BAD_REQUEST);
        }
        Agreement agreement = null;
        try {
            agreement = agreementService.acceptAgreementByCriteriaAndBooking(criteriaId,bookingId);
        } catch (BpcrsException e) {
            return new ResponseEntity(new ApiResponse<>(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        AgreementPayload.ResponseCreateAgreement agr = ObjectMapperUtils.map(agreement, AgreementPayload.ResponseCreateAgreement.class);
        return ResponseEntity.ok(new ApiResponse<>(true, agr));
    }

    @PostMapping
    @RolesAllowed(RoleEnum.RoleType.USER)
    public ResponseEntity<?> createAgreement(@Valid @RequestBody AgreementPayload.RequestCreateAgreement request) {
        Booking booking = bookingService.getBookingInformation(request.getBookingId());
        Criteria criteria = criteriaService.getCriteria(request.getCriteriaId());
        if (booking == null) {
            return new ResponseEntity(new ApiResponse<>(false, "Dont have any Booking with bookingId " + request.getBookingId()),
                    HttpStatus.BAD_REQUEST);
        }
        if (criteria == null) {
            return new ResponseEntity(new ApiResponse<>(false, "Dont have any Criteria with criteria id " + request.getCriteriaId()),
                    HttpStatus.BAD_REQUEST);
        }
        // if user=renter => insert agreement have criteria with isRenter=true
        // overwise, user=owner => insert agreement have criteria with isRenter=false
        if ((criteria.isRenter() && booking.getRenter().getId() == accountService.getCurrentUser().getId()) || (!criteria.isRenter() && booking.getCar().getOwner().getId() == accountService.getCurrentUser().getId())){

            AgreementPayload.ResponseCreateAgreement response = new AgreementPayload.ResponseCreateAgreement();
            agreementService.createOrUpdateAgreement(request).modelMaplerToObject(response, false);
            return ResponseEntity.ok(new ApiResponse<>(true, response));
        }
        return new ResponseEntity(new ApiResponse<>(false, "Action can't performed"),
                HttpStatus.BAD_REQUEST);
    }


}
