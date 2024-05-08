package apps.SpendingTracker.controllers;

import apps.SpendingTracker.services.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class ExpenseController {
    private static final Logger logger = LoggerFactory.getLogger(ExpenseController.class);

    private final ExpenseService expenseService;


    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

//    @GetMapping("/expenses")
//    public String getAllExpenses(Model model) {
//        Optional<List<Expense>> allExpenses = expenseService.getAllExpenses();
//        model.addAttribute("expenses", allExpenses.get());
//        return "dashboard";
//    }


}
