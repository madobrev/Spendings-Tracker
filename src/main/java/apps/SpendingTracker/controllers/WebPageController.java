package apps.SpendingTracker.controllers;

import apps.SpendingTracker.services.ExpenseService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.Month;

@Controller
public class WebPageController {
    private final ExpenseService expenseService;
    private final HttpSession httpSession;

    public WebPageController(ExpenseService expenseService, HttpSession httpSession) {
        this.expenseService = expenseService;
        this.httpSession = httpSession;
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
    public ModelAndView dashboard(@RequestParam(required = false, defaultValue = "0") int month, Model model) {
        String username = (String) httpSession.getAttribute("username");

        if (username == null) {
            return new ModelAndView("redirect:/home");
        }

        month = month == 0 ? LocalDate.now().getMonthValue() : month; // is it the first load (0 as param), so that the current month's spendings should be shown, or it is a result from the redirect from selecting to change the month (1-12 as param)
        String displayMonth = Month.of(month).toString().toLowerCase();
        displayMonth = displayMonth.substring(0, 1).toUpperCase() + displayMonth.substring(1);

        model.addAttribute("username", username);
        model.addAttribute("expenses", expenseService.getAllExpensesForMonth(month).orElse(null));
        model.addAttribute("month", displayMonth);

        return new ModelAndView("dashboard");
    }

    @PostMapping("/changeMonth")
    public String changeMonth(@RequestParam int month) {
        return "redirect:/dashboard?month=" + month;
    }

    @RequestMapping("/logout")
    public ModelAndView logout() {
        httpSession.setAttribute("username", null);
        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/new_expense")
    public String showNewExpenseForm(Model model) {
        return "new_expense";
    }

}

