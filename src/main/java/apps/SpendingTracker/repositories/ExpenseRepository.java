package apps.SpendingTracker.repositories;


import apps.SpendingTracker.models.Expense;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Long> {
    Optional<List<Expense>> findByAccount_Id(Long accountId);

    @Query("SELECT e FROM Expense e WHERE e.account.id = :accountId AND MONTH(e.date) = :month ORDER BY e.date ASC")
    Optional<List<Expense>> getExpensesByUserAndMonth(Long accountId, int month);

    @Modifying
    @Query("DELETE FROM Expense e WHERE e.account.id = :accountId")
    void deleteAllUserExpenses(Long accountId);


}
