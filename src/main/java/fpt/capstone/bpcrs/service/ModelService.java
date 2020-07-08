package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.model.Model;

import java.util.List;

public interface ModelService {

    Model createModel(Model newModel);

    Model getModelById(int id);

    Model updateModel(Model model, int id);

    List<Model> getAll();
}
