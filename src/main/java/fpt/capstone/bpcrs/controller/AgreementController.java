package fpt.capstone.bpcrs.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fpt.capstone.bpcrs.model.Agreement;
import fpt.capstone.bpcrs.model.Criteria;
import fpt.capstone.bpcrs.payload.AgreementPayload;
import fpt.capstone.bpcrs.payload.ApiError;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.service.AgreementService;
import fpt.capstone.bpcrs.service.BookingService;
import fpt.capstone.bpcrs.service.CriteriaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/agreement")
@Slf4j
@Validated
public class AgreementController {
    @Autowired
    private AgreementService agreementService;
    @Autowired
    private CriteriaService criteriaService;
    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> createAgreement(@JsonView(AgreementPayload.Request_CreateAgreement_Validate.class) @Valid @RequestBody AgreementPayload.RequestCreateAgreement request) {
//        Booking booking = bookingService.getBookingInformation(request.getBooking_id());
//        if (booking == null) {
//            return new ResponseEntity(new ApiError("Booking with id = " + request.getBooking_id() + " not found", ""), HttpStatus.BAD_REQUEST);
//        }


        Criteria criteria = criteriaService.getCriteria(request.getCriteria_id());
        if (criteria == null) {
            return new ResponseEntity(new ApiError("Criteria with id = " + request.getCriteria_id() + " not found", ""), HttpStatus.BAD_REQUEST);
        }
        request.setCriteria(criteria);
        Agreement agreement = agreementService.createAgreement(request.buildAgreement());
        return ResponseEntity.ok(new ApiResponse<>(true, agreement));
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<?> getAgreementsByBookingId(@PathVariable int id) {
        List<Agreement> agreements = agreementService.getListAgreementByBookingID(id);
        System.out.println("Have data? " + agreements.size());
        if (agreements.isEmpty()) {
            return new ResponseEntity(new ApiResponse<>(false, "Dont have any agreement with booking_id =" + id), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new ApiResponse<>(true, agreements));
    }

    @GetMapping()
    public ResponseEntity<?> getAgreementById(@RequestParam int id) {
        Agreement agreement = agreementService.getAgreementById(id);
        if (agreement == null) {
            return new ResponseEntity(new ApiResponse<>(false, "Dont have any agreement with id " + id), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new ApiResponse<>(true, agreement));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAgreement(@PathVariable() int id, @RequestBody AgreementPayload.RequestUpdateAgreement request) {
        boolean isExisted = agreementService.getAgreementById(id) != null;
        if (!isExisted) {
            return new ResponseEntity(new ApiError("Agreement with id = " + id + " not found", ""), HttpStatus.BAD_REQUEST);
        }
//        Booking booking = bookingService.getBookingInformation(request.getBooking_id());
//        if (booking == null) {
//            return new ResponseEntity(new ApiError("Booking with id = " + request.getBooking_id() + " not found", ""), HttpStatus.BAD_REQUEST);
//        }
//        request.setBooking(booking);
        Agreement agreement = agreementService.updateAgreement(id, request.buildAgreement());
        return ResponseEntity.ok(new ApiResponse<>(true, agreement));
    }
}
