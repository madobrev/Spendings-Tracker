package apps.SpendingTracker.services;


import apps.SpendingTracker.models.Account;
import apps.SpendingTracker.models.Category;
import apps.SpendingTracker.models.Expense;
import apps.SpendingTracker.repositories.ExpenseRepository;
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

    public void addExpense(Expense expense, String categoryName) {

        expense.setAccount(accountService.getCurrentUser().orElse(null));
        Optional<Category> category = categoryService.getCategoryByName(categoryName);
        category.ifPresent(expense::setCategory);
        expenseRepository.save(expense);
    }
}
