package apps.SpendingTracker.controllers;

import apps.SpendingTracker.services.ExpenseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebPageController {
    private final ExpenseService expenseService;

    public WebPageController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("expenses", expenseService.getAllExpenses().orElse(null));
        return "dashboard";
    }

    @RequestMapping("/new_expense")
    public String newExpense() {
        return "new_expense";
    }

}

