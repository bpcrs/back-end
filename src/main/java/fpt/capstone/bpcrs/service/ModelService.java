package fpt.capstone.bpcrs.service;

import fpt.capstone.bpcrs.model.Brand;
import fpt.capstone.bpcrs.model.Model;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ModelService {

    Model createModel(String name);

    Model getModelById(int id);

    Model updateModel(int id, String name);

    List<Model> getAll();

    Page<Model> getAllModelByAdmin(int page, int size);
}
