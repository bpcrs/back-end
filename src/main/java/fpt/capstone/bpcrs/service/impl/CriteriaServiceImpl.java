package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.component.IgnoreNullProperty;
import fpt.capstone.bpcrs.component.Paging;
import fpt.capstone.bpcrs.model.Criteria;
import fpt.capstone.bpcrs.repository.CriteriaRepository;
import fpt.capstone.bpcrs.service.CriteriaService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CriteriaServiceImpl implements CriteriaService {

    @Autowired
    private CriteriaRepository criteriaRepository;

    @Override
    public List<Criteria> getAllCriteria(int page, int size) {
        Page<Criteria> criterias = criteriaRepository.findAll(new Paging(page, size, Sort.unsorted()));
        return criterias.get().collect(Collectors.toList());
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
