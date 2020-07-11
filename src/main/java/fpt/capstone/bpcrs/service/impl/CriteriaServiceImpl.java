package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.component.IgnoreNullProperty;
import fpt.capstone.bpcrs.component.Paging;
import fpt.capstone.bpcrs.model.Criteria;
import fpt.capstone.bpcrs.repository.CriteriaRepository;
import fpt.capstone.bpcrs.service.CriteriaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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




}
