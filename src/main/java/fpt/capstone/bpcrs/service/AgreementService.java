package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.exception.BpcrsException;
import fpt.capstone.bpcrs.model.Agreement;
import fpt.capstone.bpcrs.payload.AgreementPayload;

import java.util.List;

public interface AgreementService {
    Agreement createOrUpdateAgreement(AgreementPayload.RequestCreateAgreement requestCreateAgreement);

    List<Agreement> createAgreementList(List<Agreement> agreements);

    List<Agreement> getListAgreementByBookingID(int bookingId, boolean isRenter);

    Agreement getAgreementById(int id);

    List<Agreement> updateAgreement(List<Agreement> agreements);

    Agreement acceptAgreementByCriteriaAndBooking(int criteriaId, int bookingId) throws BpcrsException;
}
