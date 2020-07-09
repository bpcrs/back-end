package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.component.IgnoreNullProperty;
import fpt.capstone.bpcrs.model.Model;
import fpt.capstone.bpcrs.repository.ModelRepository;
import fpt.capstone.bpcrs.service.ModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ModelRepository modelRepository;

    @Override
    public Model createModel(Model newModel) {
        return modelRepository.save(newModel);
    }

    @Override
    public Model getModelById(int id) {
        Optional<Model> model = modelRepository.findById(id);
        return model.orElse(null);
    }

    @Override
    public Model updateModel(Model model, int id) {
        Model updateModel = modelRepository.getOne(id);
        BeanUtils.copyProperties(model, updateModel, IgnoreNullProperty.getNullPropertyNames(model));
        return modelRepository.save(updateModel);
    }

    @Override
    public List<Model> getAll() {
        return modelRepository.findAll();
    }
}
