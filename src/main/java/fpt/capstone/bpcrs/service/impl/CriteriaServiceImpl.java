package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.component.IgnoreNullProperty;
import fpt.capstone.bpcrs.component.Paging;
import fpt.capstone.bpcrs.constant.CriteriaEnum;
import fpt.capstone.bpcrs.model.Agreement;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.model.Criteria;
import fpt.capstone.bpcrs.payload.CriteriaPayload;
import fpt.capstone.bpcrs.repository.BookingRepository;
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

    @Autowired
    private BookingRepository bookingRepository;

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
        result.setMileageLimit(Integer.parseInt(agreement.getValue()));
        agreement = getAgreementByCriteria(agreementList, CriteriaEnum.EXTRA);
        result.setExtra(Integer.parseInt(agreement.getValue()));
        agreement = getAgreementByCriteria(agreementList,CriteriaEnum.DEPOSIT);
        result.setDeposit(Integer.parseInt(agreement.getValue()) * agreement.getBooking().getCar().getPrice());
        agreement = getAgreementByCriteria(agreementList,CriteriaEnum.INSURANCE);
        JSONObject insurance = new JSONObject(agreement.getValue());
        result.setInsurance(Integer.parseInt(insurance.get("value").toString()));
        result.setEstimatePrice(result.getDeposit() + result.getInsurance() + agreement.getBooking().getRentalPrice());
        int distance = odmeter - booking.getCar().getOdometer();
        if (distance > result.getMileageLimit()){
            booking.setDistance(distance);
            result.setExtra((distance - result.getMileageLimit()) * result.getExtra());
        } else {
            result.setExtra(0);
        }
        if (odmeter != booking.getCar().getOdometer()){
            result.setTotalPrice(result.getEstimatePrice() + result.getExtra() - result.getDeposit());
            booking.setTotalPrice(result.getTotalPrice());
            bookingRepository.save(booking);
        } else {
            result.setTotalPrice(booking.getTotalPrice());
        }
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
