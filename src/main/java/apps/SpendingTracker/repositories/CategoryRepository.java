package apps.SpendingTracker.repositories;

import apps.SpendingTracker.models.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE c.name = :categoryName")
    Optional<Category> getCategory(String categoryName);

}
