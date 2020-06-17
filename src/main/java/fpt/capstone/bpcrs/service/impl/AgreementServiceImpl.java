package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.component.IgnoreNullProperty;
import fpt.capstone.bpcrs.component.Paging;
import fpt.capstone.bpcrs.model.Agreement;
import fpt.capstone.bpcrs.repository.AgreementRepository;
import fpt.capstone.bpcrs.service.AgreementService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AgreementServiceImpl implements AgreementService {

    @Autowired
    private AgreementRepository agreementRepository;

    @Override
    public Agreement createAgreement(Agreement agreement) {
        return agreementRepository.save(agreement);
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
    public Agreement updateAgreement(int id, Agreement agreement) {
        Agreement existed = agreementRepository.getOne(id);
        BeanUtils.copyProperties(agreement, existed, IgnoreNullProperty.getNullPropertyNames(agreement));
        return agreementRepository.save(existed);
    }

    @Override
    public List<Agreement> getListAgreementByCriteriaID(int criteriaId, int page, int size) {
        Page<Agreement> agreements = agreementRepository.findAllByCriteria_Id(criteriaId, new Paging(page, size, Sort.unsorted()));
        return agreements.get().collect(Collectors.toList());
    }
}
