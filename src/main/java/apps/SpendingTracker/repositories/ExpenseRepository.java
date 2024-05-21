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


    @Query("SELECT e FROM Expense e WHERE e.account.id = :accountId AND MONTH(e.date) = :month ORDER BY e.date ASC")
    Optional<List<Expense>> getExpensesByUserAndMonthSortedByDateAscending(Long accountId, int month);

    @Query("SELECT e FROM Expense e WHERE e.account.id = :accountId AND MONTH(e.date) = :month ORDER BY e.amount ASC")
    Optional<List<Expense>> getExpensesByUserAndMonthSortedByAmountAscending(Long accountId, int month);

    @Query("SELECT e FROM Expense e WHERE e.account.id = :accountId AND MONTH(e.date) = :month ORDER BY e.category ASC")
    Optional<List<Expense>> getExpensesByUserAndMonthSortedByCategoryAscending(Long accountId, int month);

    @Query("SELECT e FROM Expense e WHERE e.account.id = :accountId AND MONTH(e.date) = :month ORDER BY e.date DESC")
    Optional<List<Expense>> getExpensesByUserAndMonthSortedByDateDescending(Long accountId, int month);

    @Query("SELECT e FROM Expense e WHERE e.account.id = :accountId AND MONTH(e.date) = :month ORDER BY e.amount DESC")
    Optional<List<Expense>> getExpensesByUserAndMonthSortedByAmountDescending(Long accountId, int month);

    @Query("SELECT e FROM Expense e WHERE e.account.id = :accountId AND MONTH(e.date) = :month ORDER BY e.category DESC")
    Optional<List<Expense>> getExpensesByUserAndMonthSortedByCategoryDescending(Long accountId, int month);
}
