package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.component.IgnoreNullProperty;
import fpt.capstone.bpcrs.exception.BpcrsException;
import fpt.capstone.bpcrs.model.Agreement;
import fpt.capstone.bpcrs.payload.AgreementPayload;
import fpt.capstone.bpcrs.repository.AgreementRepository;
import fpt.capstone.bpcrs.repository.BookingRepository;
import fpt.capstone.bpcrs.repository.CriteriaRepository;
import fpt.capstone.bpcrs.service.AgreementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AgreementServiceImpl implements AgreementService {

    @Autowired
    private AgreementRepository agreementRepository;

    @Autowired
    private CriteriaRepository criteriaRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Agreement createOrUpdateAgreement(AgreementPayload.RequestCreateAgreement requestCreateAgreement) {
        Agreement agreement = agreementRepository.findByCriteria_IdAndBooking_Id(requestCreateAgreement.getCriteriaId(),requestCreateAgreement.getBookingId());
        if (agreement == null){
            agreement = (Agreement) new Agreement().buildObject(requestCreateAgreement, true);
            agreement.setBooking(bookingRepository.getOne(requestCreateAgreement.getBookingId()));
            agreement.setCriteria(criteriaRepository.getOne(requestCreateAgreement.getCriteriaId()));
            agreement.setApproved(false);
        } else {
            agreement.setValue(requestCreateAgreement.getValue());
        }
        return agreementRepository.save(agreement);
    }

    @Override
    public List<Agreement> createAgreementList(List<Agreement> agreements) {
        return agreementRepository.saveAll(agreements);
    }


    @Override
    public List<Agreement> getListAgreementByBookingID(int bookingId, boolean isRenter) {
        List<Agreement> agreements = agreementRepository.findAllByBooking_IdAndCriteria_IsRenter(bookingId, isRenter);
        return agreements;
    }

    @Override
    public Agreement getAgreementById(int id) {
        Optional<Agreement> agreement = agreementRepository.findById(id);
        System.out.println("Agreement id : " + agreement.get().getId());
        return agreement.orElse(null);
    }


    @Override
    public List<Agreement> updateAgreement(List<Agreement> agreements) {
        List<Agreement> agreementList = new ArrayList<>();
        for (Agreement agreement : agreements) {
            Agreement updateAgreement = agreementRepository.getOne(agreement.getId());
            BeanUtils.copyProperties(agreement, updateAgreement, IgnoreNullProperty.getNullPropertyNames(agreement));
            agreementList.add(updateAgreement);
        }
        return agreementRepository.saveAll(agreementList);
    }

    @Override
    public Agreement acceptAgreementByCriteriaAndBooking(int criteriaId, int bookingId) throws BpcrsException {
        Agreement agreement = agreementRepository.findByCriteria_IdAndBooking_Id(criteriaId,bookingId);
        if (agreement.isApproved()){
            throw new BpcrsException("Agreement is already accepted");
        }
        agreement.setApproved(true);
        return agreementRepository.save(agreement);
    }
}
