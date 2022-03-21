package id.ac.ui.cs.advprog.tutorial5.service;

import id.ac.ui.cs.advprog.tutorial5.model.Subcategory;
import id.ac.ui.cs.advprog.tutorial5.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Override
    public Subcategory getSubcategoryById(int id) {
        return subcategoryRepository.findById(id);
    }

    @Override
    public Iterable<Subcategory> getListSubcategory() {
        return subcategoryRepository.findAll();
    }

    @Override
    public Subcategory createSubcategory(Subcategory subcategory) {
        subcategoryRepository.save(subcategory);
        return subcategory;
    }

    @Override
    public Subcategory updateSubcategory(int id, Subcategory subcategory) {
        subcategory.setId(id);
        subcategoryRepository.save(subcategory);
        return subcategory;
    }

    @Override
    public void deleteSubcategoryById(int id) {
        Subcategory subcategory = this.getSubcategoryById(id);
        if(subcategory != null)
            subcategoryRepository.delete(subcategory);
    }
}
