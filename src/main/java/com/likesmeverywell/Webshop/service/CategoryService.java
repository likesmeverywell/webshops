package com.likesmeverywell.Webshop.service;

import com.likesmeverywell.Webshop.model.Category;
import com.likesmeverywell.Webshop.model.Product;
import com.likesmeverywell.Webshop.repository.CategoryRepository;
import com.likesmeverywell.Webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(@PathVariable Long id) {
        return categoryRepository.findById(id);
    }

    public Category addCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category updatedCategory) {
        return categoryRepository.findById(id).map(category -> {
            category.setName(updatedCategory.getName());
            category.setProducts(updatedCategory.getProducts());
            return categoryRepository.save(category);
        }).orElseThrow(() -> new RuntimeException("Category not found with id " + id));
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }


    public void addProductToCategory(@PathVariable Long categoryId, @PathVariable Long productId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setCategory(category);
        productRepository.save(product);
    }

    public List<Product> getProductsByCategory(@PathVariable Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
}
