package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.model.Agreement;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AgreementService {
    Agreement createAgreement(Agreement agreement);

    List<Agreement> getListAgreementByBookingID(int bookingId, int page, int size);

    Agreement getAgreementById(int id);

    Agreement updateAgreement(int id, Agreement agreement);
}
