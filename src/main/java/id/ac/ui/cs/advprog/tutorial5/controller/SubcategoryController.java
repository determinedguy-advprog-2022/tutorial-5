package id.ac.ui.cs.advprog.tutorial5.controller;

import id.ac.ui.cs.advprog.tutorial5.model.Subcategory;
import id.ac.ui.cs.advprog.tutorial5.service.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/subcategory")
public class SubcategoryController {
    @Autowired
    private SubcategoryService subcategoryService;

    @PostMapping(produces = {"application/json"})
    @ResponseBody
    public ResponseEntity postSubcategory(@RequestBody Subcategory subcategory) {
        return ResponseEntity.ok(subcategoryService.createSubcategory(subcategory));
    }

    @GetMapping(produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Iterable<Subcategory>> getListSubcategory() {
        return ResponseEntity.ok(subcategoryService.getListSubcategory());
    }
    @GetMapping(path = "/{id}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity getSubcategory(@PathVariable(value = "id") int id) {
        Subcategory subcategory = subcategoryService.getSubcategoryById(id);
        if (subcategory == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(subcategory);
    }

    @PutMapping(path = "/{id}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity updateSubcategory(@PathVariable(value = "id") int id, @RequestBody Subcategory subcategory) {
        return ResponseEntity.ok(subcategoryService.updateSubcategory(id, subcategory));
    }

    @DeleteMapping(path = "/{id}", produces = {"application/json"})
    public ResponseEntity deleteSubcategory(@PathVariable(value = "id") int id) {
        subcategoryService.deleteSubcategoryById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
