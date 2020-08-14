package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.constant.CriteriaEnum;
import fpt.capstone.bpcrs.model.Agreement;
import fpt.capstone.bpcrs.model.Booking;
import fpt.capstone.bpcrs.model.Criteria;
import fpt.capstone.bpcrs.payload.CriteriaPayload;

import java.util.List;

public interface CriteriaService {

    List<Criteria> getAllCriteria();

    Criteria createCriteria(Criteria criteria);

    Criteria updateCriteria(Criteria criteria, int id);

    Criteria getCriteria(int id);

    Criteria findCriteriaByName(String name);

    CriteriaPayload.PreReturnResponse estimatePriceByAgreement(List<Agreement> agreementList, Booking booking, int odmeter) throws Exception;
}
