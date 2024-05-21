package apps.SpendingTracker.services;


import apps.SpendingTracker.models.Account;
import apps.SpendingTracker.models.Category;
import apps.SpendingTracker.models.Expense;
import apps.SpendingTracker.repositories.ExpenseRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private final AccountService accountService;
    private final ExpenseRepository expenseRepository;
    private final CategoryService categoryService;

    public ExpenseService(AccountService accountService, ExpenseRepository expenseRepository, CategoryService categoryService) {
        this.accountService = accountService;
        this.expenseRepository = expenseRepository;
        this.categoryService = categoryService;
    }

    public Optional<List<Expense>> getAllExpensesForMonth(int month) {
        if (accountService.getCurrentUser().isPresent()) {
            Account currentUser = accountService.getCurrentUser().get();
            long user_id = currentUser.getId();
            return expenseRepository.getExpensesByUserAndMonth(user_id, month);
        }
        return Optional.empty();
    }

    public Optional<List<Expense>> filterExpenses(Long accountID, int month, String filter, boolean ascending) {

        return switch (filter) {
            case "Date":
                yield ascending ? expenseRepository.getExpensesByUserAndMonthSortedByDateAscending(accountID, month) : expenseRepository.getExpensesByUserAndMonthSortedByDateDescending(accountID, month);
            case "Category":
                yield ascending ? expenseRepository.getExpensesByUserAndMonthSortedByCategoryAscending(accountID, month) : expenseRepository.getExpensesByUserAndMonthSortedByCategoryDescending(accountID, month);
            case "Amount":
                yield ascending ? expenseRepository.getExpensesByUserAndMonthSortedByAmountAscending(accountID, month) : expenseRepository.getExpensesByUserAndMonthSortedByAmountDescending(accountID, month);
            default:
                throw new IllegalStateException("Unexpected value: " + filter);
        };
    }


    public void addExpense(Expense expense, String categoryName) {

        expense.setAccount(accountService.getCurrentUser().orElse(null));
        Optional<Category> category = categoryService.getCategoryByName(categoryName);
        category.ifPresent(expense::setCategory);
        expenseRepository.save(expense);
    }

    @Transactional
    public void deleteExpenses(List<Long> expenseIDs) {
        for (Long expenseID : expenseIDs) {
            expenseRepository.deleteById(expenseID);
        }
    }

}
