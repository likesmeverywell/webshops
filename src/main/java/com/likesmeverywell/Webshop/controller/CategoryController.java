package com.likesmeverywell.Webshop.controller;

import com.likesmeverywell.Webshop.model.Category;
import com.likesmeverywell.Webshop.model.Product;
import com.likesmeverywell.Webshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.addCategory(category);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            Category updatedCategory = categoryService.updateCategory(id, category);
            return ResponseEntity.ok(updatedCategory);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<Void> addProductToCategory(@PathVariable Long categoryId, @PathVariable Long productId) {
        categoryService.addProductToCategory(categoryId, productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{categoryId}/products")
    public List<Product> getProductsByCategory(@PathVariable Long categoryId) {
        return categoryService.getProductsByCategory(categoryId);
    }
}
