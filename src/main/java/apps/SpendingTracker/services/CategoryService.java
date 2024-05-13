package apps.SpendingTracker.services;


import apps.SpendingTracker.models.Category;
import apps.SpendingTracker.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public Optional<Category> getCategoryByName(String categoryName) {
        return categoryRepository.getCategory(categoryName);
    }

}
