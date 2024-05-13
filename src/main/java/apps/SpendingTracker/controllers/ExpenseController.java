package apps.SpendingTracker.controllers;

import apps.SpendingTracker.models.Expense;
import apps.SpendingTracker.services.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class ExpenseController {
    private static final Logger logger = LoggerFactory.getLogger(ExpenseController.class);

    private final ExpenseService expenseService;


    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping("/addExpense")
    public String addExpense(@RequestParam("date") LocalDate date,
                             @RequestParam("amount") double amount,
                             @RequestParam("category") String category,
                             @RequestParam("description") String description) {


        Expense newExpense = new Expense(amount, description, date);
        expenseService.addExpense(newExpense, category);
        return "redirect:/dashboard";
    }
}
