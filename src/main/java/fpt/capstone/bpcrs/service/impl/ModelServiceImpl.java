package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.component.Paging;
import fpt.capstone.bpcrs.model.Model;
import fpt.capstone.bpcrs.repository.ModelRepository;
import fpt.capstone.bpcrs.service.ModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ModelRepository modelRepository;

    @Override
    public Model createModel(String name) {
        return modelRepository.save(Model.builder().name(name).build());
    }

    @Override
    public Model getModelById(int id) {
        return modelRepository.findById(id).orElse(null);
    }

    @Override
    public Model updateModel(int id, String name) {
        Model model = getModelById(id);
        if (model != null) {
            model.setName(name);
            modelRepository.save(model);
        }
        return model;
    }

    @Override
    public List<Model> getAll() {
        return modelRepository.findAll();
    }

    @Override
    public Page<Model> getAllModelByAdmin(int page, int size) {
        return modelRepository.findAll(new Paging(page, size, Sort.unsorted()));
    }


}
