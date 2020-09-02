package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.exception.BpcrsException;

public interface JobService {

    void cancelBookingOvertimeAgreements() throws BpcrsException;
}
