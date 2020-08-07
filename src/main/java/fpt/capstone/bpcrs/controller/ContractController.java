package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.constant.BookingEnum;
import fpt.capstone.bpcrs.constant.RoleEnum;
import fpt.capstone.bpcrs.model.Account;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.service.AccountService;
import fpt.capstone.bpcrs.service.BlockchainService;
import fpt.capstone.bpcrs.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/contract")
@Slf4j
public class ContractController {
    @Autowired
    private BlockchainService blockchainService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private AccountService accountService;

    @GetMapping()
    @RolesAllowed({RoleEnum.RoleType.USER})
    public ResponseEntity<?> signContract(@RequestParam int id) {
        try {
            Account currentUser = accountService.getCurrentUser();
            Booking booking = bookingService.getBookingInformation(id);
            if (booking != null){
                if (booking.getStatus() != BookingEnum.CONFIRM){
                    return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Invalid request"));
                }
                boolean isSuccess = blockchainService.signingContract(booking,booking.getCar().getOwner().equals(currentUser));
                if (isSuccess) {
                    bookingService.updateBookingStatus(booking, BookingEnum.DONE);
                    return ResponseEntity.ok().body(new ApiResponse<>(true, "Signed contract with booking id=" + id, HttpStatus.OK));
                }
            }
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Can't signed contract with booking id= " + id));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ApiResponse<>(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
