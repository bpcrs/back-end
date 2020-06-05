package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.component.IgnoreNullProperty;
import fpt.capstone.bpcrs.component.Paging;
import fpt.capstone.bpcrs.model.Agreement;
import fpt.capstone.bpcrs.repository.AgreementRepository;
import fpt.capstone.bpcrs.service.AgreementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<Agreement> getListAgreementByBookingID(int bookingId, int page, int size) {
        Page<Agreement> agreements = agreementRepository.findAll(new Paging(page, size, Sort.unsorted()));
        System.out.println("size: " + agreements.getSize());

        return agreements.get().collect(Collectors.toList());
    }

    @Override
    public Agreement getAgreementById(int id) {
        Optional<Agreement> agreement = agreementRepository.findById(id);
        System.out.println("Agreement data : " + agreement.toString());
        return agreement.orElse(null);
    }


    @Override
    public Agreement updateAgreement(int id, Agreement agreement) {
        Agreement existed = agreementRepository.getOne(id);
        BeanUtils.copyProperties(agreement, existed, IgnoreNullProperty.getNullPropertyNames(agreement));
        return agreementRepository.save(existed);
    }
}
