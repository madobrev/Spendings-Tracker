package apps.SpendingTracker.controllers;

import apps.SpendingTracker.models.Category;
import apps.SpendingTracker.models.Expense;
import apps.SpendingTracker.services.CategoryService;
import apps.SpendingTracker.services.ExpenseService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ExpenseController {
    private static final Logger logger = LoggerFactory.getLogger(ExpenseController.class);

    private final ExpenseService expenseService;

    private final CategoryService categoryService;


    public ExpenseController(ExpenseService expenseService, CategoryService categoryService) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
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

    //TODO: change the redirect to the filtered month if one is present
    @PostMapping("/deleteExpenses")
    public String deleteExpenses(@RequestBody List<Long> expenseIds, Model model) {
        expenseService.deleteExpenses(expenseIds);
        return "redirect:/dashboard";
    }

    @PostMapping("/setExpensesToEdit")
    public String setExpensesToEdit(@RequestBody List<Long> expenseIds, HttpSession session) {
        List<Expense> expenses = expenseService.getExpensesByExpenseID(expenseIds);
        session.setAttribute("expenses", expenses);
        return "redirect:/edit_expenses";
    }

    @RequestMapping("/edit_expenses")
    public String editExpenses(Model model, HttpSession session) {
        List<Expense> expenses = (List<Expense>) session.getAttribute("expenses");
        model.addAttribute("expenses", expenses);
        return "edit_expenses";
    }

    @PostMapping("/applyEditExpenses")
    public String applyEditExpenses(@RequestParam("expenseIds") List<Long> expenseIds,
                                    @RequestParam("date") List<LocalDate> dates,
                                    @RequestParam("amount") List<Double> amounts,
                                    @RequestParam("category") List<String> categories,
                                    @RequestParam("description") List<String> descriptions,
                                    Model model) {

        List<Expense> updatedExpenses = new ArrayList<>();

        for (int i = 0; i < expenseIds.size(); i++) {
            Long expenseId = expenseIds.get(i);
            LocalDate newExpenseDate = dates.get(i);
            Double newExpenseAmount = amounts.get(i);
            String newExpenseDescription = descriptions.get(i);

            Expense updatedExpense = new Expense(expenseId, newExpenseAmount, newExpenseDescription, newExpenseDate);

            String newExpenseCategory = categories.get(i);
            Optional<Category> category = categoryService.getCategoryByName(newExpenseCategory);
            category.ifPresent(updatedExpense::setCategory);
            updatedExpenses.add(updatedExpense);
        }

        expenseService.updateExpenses(updatedExpenses);
        return "redirect:/dashboard";
    }


}
