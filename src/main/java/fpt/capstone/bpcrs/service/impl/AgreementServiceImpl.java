package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.component.IgnoreNullProperty;
import fpt.capstone.bpcrs.component.Paging;
import fpt.capstone.bpcrs.constant.BookingEnum;
import fpt.capstone.bpcrs.model.Agreement;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.model.Criteria;
import fpt.capstone.bpcrs.repository.AgreementRepository;
import fpt.capstone.bpcrs.repository.BookingRepository;
import fpt.capstone.bpcrs.repository.CriteriaRepository;
import fpt.capstone.bpcrs.service.AgreementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Agreement createAgreement(Agreement agreement) {
        return agreementRepository.save(agreement);
    }

    @Override
    public List<Agreement> createAgreementList(List<Agreement> agreements) {
        return agreementRepository.saveAll(agreements);
    }


    @Override
    public List<Agreement> getListAgreementByBookingID(int bookingId) {
        List<Agreement> agreements = agreementRepository.findAllByBooking_id(bookingId);

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

        return agreementRepository.saveAll(agreements);
    }

    @Override
    public List<Agreement> createAgreementListRequest(int bookingId) {
        Booking booking = bookingRepository.getOne(bookingId);
        List<Criteria> criteriaList = criteriaRepository.findAll();
        List<Agreement> agreements = new ArrayList<>();
        for (Criteria criteria : criteriaList) {
            Agreement agreement = Agreement.builder().booking(booking).criteria(criteria)
                    .value(30).isApproved(false).build();
            agreements.add(agreement);
        }

        return agreementRepository.saveAll(agreements);
    }
}
