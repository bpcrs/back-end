package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.component.IgnoreNullProperty;
import fpt.capstone.bpcrs.component.Paging;
import fpt.capstone.bpcrs.constant.CriteriaEnum;
import fpt.capstone.bpcrs.model.Agreement;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.model.Criteria;
import fpt.capstone.bpcrs.payload.CriteriaPayload;
import fpt.capstone.bpcrs.repository.CriteriaRepository;
import fpt.capstone.bpcrs.service.CriteriaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CriteriaServiceImpl implements CriteriaService {

    @Autowired
    private CriteriaRepository criteriaRepository;

    @Override
    public List<Criteria> getAllCriteria() {

        return criteriaRepository.findAll();
    }

    @Override
    public Criteria createCriteria(Criteria criteria) {
        return criteriaRepository.save(criteria);
    }

    @Override
    public Criteria updateCriteria(Criteria criteria, int id) {
        Criteria exist = criteriaRepository.getOne(id);
        BeanUtils.copyProperties(criteria, exist, IgnoreNullProperty.getNullPropertyNames(criteria));
        return criteriaRepository.save(exist);
    }

    @Override
    public Criteria getCriteria(int id) {
        Optional<Criteria> criteria = criteriaRepository.findById(id);
        return criteria.orElse(null);
    }

    @Override
    public Criteria findCriteriaByName(String name) {
        Optional<Criteria> criteria = criteriaRepository.findByName(name);
        return criteria.orElse(null);
    }

    @Override
    public CriteriaPayload.PreReturnResponse estimatePriceByAgreement(List<Agreement> agreementList, Booking booking, int odmeter) throws Exception {
        CriteriaPayload.PreReturnResponse result = new CriteriaPayload.PreReturnResponse();
        Agreement agreement = getAgreementByCriteria(agreementList, CriteriaEnum.MILEAGE_LIMIT);
        result.setMileageLimit(odmeter - agreement.getBooking().getCar().getOdometer());
        agreement = getAgreementByCriteria(agreementList, CriteriaEnum.EXTRA);
        result.setExtra(0);
        if (result.getMileageLimit() > 0){
            result.setExtra(Integer.parseInt(agreement.getValue()) * result.getMileageLimit());
        }
        agreement = getAgreementByCriteria(agreementList,CriteriaEnum.DEPOSIT);
        result.setDeposit(Integer.parseInt(agreement.getValue()) * agreement.getBooking().getCar().getPrice());
        agreement = getAgreementByCriteria(agreementList,CriteriaEnum.INSURANCE);
        JSONObject insurance = new JSONObject(agreement.getValue());
        result.setInsurance(Integer.parseInt(insurance.get("value").toString()));
        result.setTotalPrice(result.getDeposit() - result.getExtra() + result.getInsurance() - agreement.getBooking().getRentalPrice());
        return result;
    }

    private Agreement getAgreementByCriteria(List<Agreement> agreementList, CriteriaEnum name){
        for (Agreement agreement : agreementList) {
            if (agreement.getCriteria().getName() == name){
                return agreement;
            }
        }
        return null;
    }


}
