package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.exception.BpcrsException;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.service.BookingService;
import fpt.capstone.bpcrs.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component("scheduledAnnotation")
public class JobServiceImpl implements JobService {

    @Autowired
    private BookingService bookingService;
    //0 0 0 1/1 * ? *
    @Override
    @Scheduled(cron = "0 0 12 * * ?")
    public void cancelBookingOvertimeAgreements() throws BpcrsException {
        List<Booking> bookingList = bookingService.updateBookingOvertimeAgreement();
        bookingList.forEach(booking -> log.info("[JOB OVERTIME executed with booking \"" + booking.getId()  +"\"]"));
    }
}
