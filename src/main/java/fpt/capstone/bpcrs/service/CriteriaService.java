package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.model.Criteria;

import java.util.List;

public interface CriteriaService {

    List<Criteria> getAllCriteria(int page, int size);

    Criteria createCriteria(Criteria criteria);

    Criteria updateCriteria(Criteria criteria, int id);

    Criteria getCriteria(int id);
}
