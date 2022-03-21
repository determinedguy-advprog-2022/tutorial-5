package id.ac.ui.cs.advprog.tutorial5.service;

import id.ac.ui.cs.advprog.tutorial5.model.Subcategory;

public interface SubcategoryService {
    Iterable<Subcategory> getListSubcategory();

    Subcategory createSubcategory(Subcategory subcategory);

    Subcategory getSubcategoryById(int id);

    Subcategory updateSubcategory(int id, Subcategory subcategory);

    void deleteSubcategoryById(int id);
}
