package fpt.capstone.bpcrs.controller;

import fpt.capstone.bpcrs.constant.BookingEnum;
import fpt.capstone.bpcrs.constant.CarEnum;
import fpt.capstone.bpcrs.constant.RoleEnum;
import fpt.capstone.bpcrs.model.Account;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.payload.ApiResponse;
import fpt.capstone.bpcrs.payload.BookingPayload;
import fpt.capstone.bpcrs.payload.DappPayload;
import fpt.capstone.bpcrs.service.AccountService;
import fpt.capstone.bpcrs.service.BlockchainService;
import fpt.capstone.bpcrs.service.BookingService;
import fpt.capstone.bpcrs.service.CarService;
import fpt.capstone.bpcrs.util.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private CarService carService;

    @PostMapping()
    @RolesAllowed({RoleEnum.RoleType.USER})
    public ResponseEntity<?> signContract(@RequestParam int id, @RequestParam String otp) {
        try {
            Account currentUser = accountService.getCurrentUser();
            Booking booking = bookingService.getBookingInformation(id);
            if (booking != null){
                if (booking.getStatus() != BookingEnum.CONFIRM && booking.getStatus() != BookingEnum.RENTER_SIGNED){
                    return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Contract must confirm before sign"));
                }
                if (booking.getCar().getOwner().equals(currentUser) && booking.getStatus() == BookingEnum.CONFIRM){
                    return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Renter must signing contract first"));
                }
                if (booking.getStatus() == BookingEnum.RENTER_SIGNED && booking.getRenter().getId().equals(currentUser.getId())){
                    return ResponseEntity.badRequest().body(new ApiResponse<>(false, "You already signed"));
                }
                //if success cont. otherwise throw EX
                accountService.confirmOTP(currentUser.getAuthyId(),otp);
                DappPayload.ResultChaincode resultChaincode = blockchainService.signingContract(booking,booking.getCar().getOwner().equals(currentUser));
                if (resultChaincode.isSuccess()) {
                    if (booking.getStatus() == BookingEnum.RENTER_SIGNED){
                        carService.updateCarStatus(booking.getCar(), CarEnum.RENTING);
                        booking = bookingService.updateBookingStatus(booking, BookingEnum.PROCESSING);
                    } else {
                        booking = bookingService.updateBookingStatus(booking, BookingEnum.RENTER_SIGNED);
                    }
                    BookingPayload.ResponseCreateBooking response = ObjectMapperUtils.map(booking,
                            BookingPayload.ResponseCreateBooking.class);
                    return ResponseEntity.ok(new ApiResponse<>(true, "Booking was signed successfully" ,response));
                } else {
                    return ResponseEntity.badRequest().body(new ApiResponse<>(false, resultChaincode.getData(), HttpStatus.OK));
                }
            }
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Can't signed contract with booking id= " + id));
        } catch (Exception e) {
            return new ResponseEntity(new ApiResponse<>(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
