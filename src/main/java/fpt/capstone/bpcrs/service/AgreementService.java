package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.model.Agreement;

import java.util.List;

public interface AgreementService {
    Agreement createAgreement(Agreement agreement);

    List<Agreement> getListAgreementByBookingID(int bookingId);

    Agreement getAgreementById(int id);

    Agreement updateAgreement(int id, Agreement agreement);

    List<Agreement> getListAgreementByCriteriaID(int bookingId, int page, int size);
}
