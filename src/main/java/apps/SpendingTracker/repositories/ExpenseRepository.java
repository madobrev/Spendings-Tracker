package apps.SpendingTracker.repositories;


import apps.SpendingTracker.models.Expense;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Long> {
    Optional<List<Expense>> findByAccount_Id(Long accountId);
}