package apps.SpendingTracker.services;


import apps.SpendingTracker.models.Account;
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

    public ExpenseService(AccountService accountService, ExpenseRepository expenseRepository) {
        this.accountService = accountService;
        this.expenseRepository = expenseRepository;
    }

    public Optional<List<Expense>> getAllExpenses() {

        if (accountService.getCurrentUser().isPresent()) {
            Account currentUser = accountService.getCurrentUser().get();
            long user_id = currentUser.getId();
            return expenseRepository.findByAccount_Id(user_id);
        }


        return null;
    }
}
