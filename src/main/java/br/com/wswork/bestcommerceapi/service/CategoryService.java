package br.com.wswork.bestcommerceapi.service;

import br.com.wswork.bestcommerceapi.exception.CategoryAlreadyExitsException;
import br.com.wswork.bestcommerceapi.exception.CategoryNotFoundException;
import br.com.wswork.bestcommerceapi.model.Category;
import br.com.wswork.bestcommerceapi.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService (CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {

        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {

        Optional<Category> category = categoryRepository.findById(id);

        return category.orElseThrow(() -> new CategoryNotFoundException("Category with id " + id + " not found."));
    }

    public Category addCategory(Category newCategory) {

        Optional<Category> existingCategory = categoryRepository.findByName(newCategory.getName());

        if (existingCategory.isPresent())
            throw new CategoryAlreadyExitsException("Category with name " + newCategory.getName() + " already exists.");

        categoryRepository.save(newCategory);

        return newCategory;
    }

    public Category modifyCategory(Category modifiedCategory, Long id) {

        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty())
            throw new CategoryNotFoundException("Category with id " + id + " not found.");

        category.get().setId(modifiedCategory.getId());
        category.get().setName(modifiedCategory.getName());

        categoryRepository.save(category.get());

        return category.get();
    }

    public void deleteCategoryById(Long id) {

        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty())
            throw new CategoryNotFoundException("Category with id " + id + " not found.");

        categoryRepository.deleteById(id);
    }
}