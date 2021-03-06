package fpt.capstone.bpcrs.controller;

import com.authy.AuthyException;
import fpt.capstone.bpcrs.constant.BookingEnum;
import fpt.capstone.bpcrs.constant.CarEnum;
import fpt.capstone.bpcrs.constant.RoleEnum;
import fpt.capstone.bpcrs.exception.BadRequestException;
import fpt.capstone.bpcrs.exception.BpcrsException;
import fpt.capstone.bpcrs.model.Account;
import fpt.capstone.bpcrs.model.Agreement;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.model.Car;
import fpt.capstone.bpcrs.payload.*;
import fpt.capstone.bpcrs.service.*;
import fpt.capstone.bpcrs.util.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.Pipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/booking")
@Slf4j
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private CarService carService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CriteriaService criteriaService;

    @Autowired
    private BlockchainService blockchainService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/renting/{id}")
    @RolesAllowed({RoleEnum.RoleType.USER, RoleEnum.RoleType.ADMINISTRATOR})
    public ResponseEntity<?> getUserRentingBookingList(@PathVariable("id") int id) {
        List<Booking> bookings = bookingService.getUserRentingBookingList(id);
        if (bookings.isEmpty()) {
            return new ResponseEntity(new ApiResponse<>(false, "Dont have any user rent with id = " + id),
                    HttpStatus.BAD_REQUEST);
        }
        List<BookingPayload.ResponseCreateBooking> responses = ObjectMapperUtils.mapAll(bookings,
                BookingPayload.ResponseCreateBooking.class);
        return ResponseEntity.ok(new ApiResponse<>(true, "Get list booking successful", responses));
    }

    @GetMapping("/{id}")
    @RolesAllowed({RoleEnum.RoleType.USER, RoleEnum.RoleType.ADMINISTRATOR})
    public ResponseEntity<?> getBooking(@PathVariable() int id) {
        BookingPayload.ResponseCreateBooking response = new BookingPayload.ResponseCreateBooking();
        Booking booking = bookingService.getBookingInformation(id);
        System.out.println(booking.toString());
        if (booking != null) {
            booking.modelMaplerToObject(response, false);
            return ResponseEntity.ok(new ApiResponse<>(true, response));
        }
        return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Booking with id = " + id + " not found",
                HttpStatus.BAD_REQUEST));

    }

    @PostMapping
    @RolesAllowed(RoleEnum.RoleType.USER)
    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingPayload.RequestCreateBooking request) throws AuthyException {
        Car car = carService.getCarById(request.getCarId());
        Account renter = accountService.getAccountById(request.getRenterId());

        if (!(renter.isLicenseCheck() && accountService.verifyAccounnt(renter.getAuthyId()))) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "User is not eligible to book",
                    HttpStatus.BAD_REQUEST));
        }

        BookingPayload.ResponseCreateBooking response = new BookingPayload.ResponseCreateBooking();
        Booking booking = Booking.builder().car(car).renter(renter)
                .fromDate(request.getFromDate()).toDate(request.getToDate())
                .location(request.getLocation()).destination(request.getDestination())
                .status(BookingEnum.REQUEST).rentalPrice(request.getTotalPrice()).build();
        try {
            carService.updateCarStatus(car, CarEnum.RENTING);
            bookingService.createBooking(booking).modelMaplerToObject(response, false);
        } catch (BpcrsException e) {
            return new ResponseEntity(new ApiError(e.getMessage(), ""),
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new ApiResponse<>(true, response));
    }

    @PutMapping("/{id}")
    @RolesAllowed(RoleEnum.RoleType.USER)
    public ResponseEntity<?> updateBookingStatus(@PathVariable("id") int id, @Valid @RequestParam BookingEnum status) {
        try {
            Booking booking = bookingService.getBookingInformation(id);
            if (booking == null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Booking with id " + id + " not " +
                        "existed", null));
            }
            BookingEnum nextStatus = status;
            List<Booking> duplicateList = new ArrayList<>();
            BookingEnum currentStatus = booking.getStatus();
            if (booking.getCar().getOwner().getId().intValue() != accountService.getCurrentUser().getId().intValue() && booking.getRenter().getId().intValue() != accountService.getCurrentUser().getId().intValue()) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "User is not allowed", null));
            }
            // if duplicate ngày booking thì sẽ cancel những request khônng được approve
            if (currentStatus == BookingEnum.REQUEST && nextStatus == BookingEnum.PENDING) {
                duplicateList = bookingService.updateBookingDuplicateDate(booking, BookingEnum.DENY);

            }
            // cancel booking thì những request đã bị deny -> request
            if (currentStatus == BookingEnum.PENDING && nextStatus == BookingEnum.CANCEL) {
                duplicateList = bookingService.updateBookingDuplicateDate(booking, BookingEnum.REQUEST);
            }
            //Save to BLC
            if (nextStatus == BookingEnum.CONFIRM || nextStatus == BookingEnum.OWNER_ACCEPTED) {
                //check agreement before => CONFIRM
                if (booking.getAgreements().isEmpty()){
                    return ResponseEntity.badRequest().body(new ApiResponse<>(false, "You must agreement before submit "
                            , null));
                }
                boolean isApproveAllAgreemet = booking.getAgreements().stream().allMatch(Agreement::isApproved);
                if (!isApproveAllAgreemet) {
                    return ResponseEntity.badRequest().body(new ApiResponse<>(false, "All agreement must be approved"
                            , null));
                }
                boolean isSuccess = blockchainService.submitContract(booking);
                if (!isSuccess) {
                    return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Can't store in blockchain " +
                            "network", null));
                }
                carService.updateCarStatus(booking.getCar(), CarEnum.RENTING);
            }
            if (nextStatus == BookingEnum.DONE) {
                CriteriaPayload.PreReturnResponse returnResponse =
                        criteriaService.estimatePriceByAgreement(booking.getAgreements(), booking,
                                booking.getDistance());
                BookingEnum[] listBookingStatusCarJoining = new BookingEnum[]{ BookingEnum.PENDING, BookingEnum.PROCESSING,BookingEnum.RENTER_SIGNED,BookingEnum.OWNER_ACCEPTED, BookingEnum.CONFIRM };
                List<Booking> listBookingCarJoining = bookingService.getAllBookingsRequestCar(booking.getCar().getId(),listBookingStatusCarJoining);
                Car car = booking.getCar();
                car.setOdometer(car.getOdometer() + booking.getDistance());
                carService.updateCar(car,booking.getCar().getId());
                booking.setTotalPrice(returnResponse.getTotalPrice());
                if (listBookingCarJoining.size() == 1){
                    carService.updateCarStatus(car, CarEnum.UNAVAILABLE);
                }
            }
            if (nextStatus == BookingEnum.CANCEL){
                carService.updateCarStatus(booking.getCar(),CarEnum.UNAVAILABLE);
            }
            booking = bookingService.updateBookingStatus(booking, status);
            BookingPayload.ResponseCreateBooking response = ObjectMapperUtils.map(booking,
                    BookingPayload.ResponseCreateBooking.class);
            if (!duplicateList.isEmpty()){
                response.setDuplicateList(ObjectMapperUtils.mapAll(duplicateList,BookingPayload.ResponseCreateBooking.class));
            }
            return ResponseEntity.ok(new ApiResponse<>(true, "Booking status was updated", response));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null));
        }
    }

    @GetMapping("/car/{id}")
    @RolesAllowed({RoleEnum.RoleType.USER, RoleEnum.RoleType.ADMINISTRATOR})
    public ResponseEntity<?> getAllBookingRequestByCar(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable() int id,
            @Valid @RequestParam BookingEnum[] status) {
        Page<Booking> bookings = bookingService.getAllBookingsRequestByCar(id, status, page, size);
        List<BookingPayload.ResponseCreateBooking> responses = ObjectMapperUtils.mapAll(bookings.toList(),
                BookingPayload.ResponseCreateBooking.class);
        PagingPayload pagingPayload =
                PagingPayload.builder().data(responses).count((int) bookings.getTotalElements()).build();
        return ResponseEntity.ok(new ApiResponse<>(true, pagingPayload));
    }

    @GetMapping("/user/{id}")
    @RolesAllowed({RoleEnum.RoleType.USER, RoleEnum.RoleType.ADMINISTRATOR})
    public ResponseEntity<?> getAllBookingRequestsByUser(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable() int id,
            @Valid @RequestParam BookingEnum[] status,
            @RequestParam boolean isRenter) {
        Page<Booking> bookings;
        if (isRenter) {
            bookings = bookingService.getAllBookingRequestsByRenter(id, status, page, size);
        } else {
            bookings = bookingService.getAllBookingRequestsByOwner(id, status, page, size);
        }
        if (bookings == null) {
            String role = isRenter ? "renter" : "owner";
            return ResponseEntity.badRequest().body(new ApiResponse<>(false,
                    "Dont have any bookings with user id = " + id + " with role = " + role, HttpStatus.BAD_REQUEST));
        }
        List<BookingPayload.ResponseCreateBooking> responses = ObjectMapperUtils.mapAll(bookings.toList(),
                BookingPayload.ResponseCreateBooking.class);
        PagingPayload pagingPayload =
                PagingPayload.builder().data(responses).count((int) bookings.getTotalElements()).build();
        return ResponseEntity.ok(new ApiResponse<>(true, pagingPayload));
    }

    @PostMapping("/estimate/{id}")
    @RolesAllowed({RoleEnum.RoleType.USER})
    public ResponseEntity<?> estimatePriceByBooking(@PathVariable int id) {
        Booking booking = bookingService.getBookingInformation(id);
        if (booking == null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Booking with id " + id + " not existed"
                    , null));
        }
        List<Agreement> agreementList = booking.getAgreements();
        try {
            boolean isApproveAllAgreemet = agreementList.stream().allMatch(Agreement::isApproved);
            if (!agreementList.isEmpty()){
                CriteriaPayload.PreReturnResponse response = criteriaService.estimatePriceByAgreement(agreementList,
                        booking, booking.getCar().getOdometer());
                if (!isApproveAllAgreemet){
                    response.setEstimatePrice(0);
                    response.setTotalPrice(0);
                }
                response.setAgreements(ObjectMapperUtils.mapAll(agreementList,
                        AgreementPayload.ResponsePreReturn.class));

                return ResponseEntity.ok().body(new ApiResponse<>(true, response));
            } else {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false,"Booking did't had any agreements"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage()));
        }
    }


    @PostMapping("/return/{id}")
    @RolesAllowed({RoleEnum.RoleType.USER})
    public ResponseEntity<?> returnPriceByBooking(@Valid @RequestParam int odometer, @PathVariable int id) {
        Booking booking = bookingService.getBookingInformation(id);
        if (booking == null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Booking with id " + id + " not existed"
                    , null));
        }
        if (!accountService.getCurrentUser().getId().equals(booking.getCar().getOwner().getId())) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "User is not eligible to executed"
                    , null));
        }
        List<Agreement> agreementList = booking.getAgreements();
        try {
            boolean isApproveAllAgreemet = agreementList.stream().allMatch(Agreement::isApproved);
            CriteriaPayload.PreReturnResponse response = criteriaService.estimatePriceByAgreement(agreementList,
                    booking, odometer);
            if (!isApproveAllAgreemet){
                response.setEstimatePrice(0);
                response.setTotalPrice(0);
            }
            response.setAgreements(ObjectMapperUtils.mapAll(agreementList,
                    AgreementPayload.ResponsePreReturn.class));

            return ResponseEntity.ok().body(new ApiResponse<>(true, response));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage()));
        }
    }

    @PostMapping("/review/{id}")
    @RolesAllowed(RoleEnum.RoleType.USER)
    public ResponseEntity<?> isBookingReviewYet(@PathVariable int id, @Valid @RequestParam int renterId) {
        Booking booking = bookingService.getBookingInformation(id);
        if (booking == null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Booking with id " + id + " not existed"
                    , null));
        }
        if (booking.getStatus() == BookingEnum.DONE) {
        boolean isReview = reviewService.checkUserCanReview(id, renterId);
        if (isReview) {
            return ResponseEntity.ok().body(new ApiResponse<>(true, true));
        } else  {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, false));
        }
        } else  {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Booking with id " + id + " not done yet"
                    , null));
        }
    }

}
