package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.model.Agreement;

import java.util.List;

public interface AgreementService {
    Agreement createAgreement(Agreement agreement);

    List<Agreement> createAgreementList(List<Agreement> agreements);

    List<Agreement> getListAgreementByBookingID(int bookingId, boolean isRenter);

    Agreement getAgreementById(int id);

    List<Agreement> updateAgreement(List<Agreement> agreements);

    List<Agreement> createAgreementListRequest(int bookingId);
}
