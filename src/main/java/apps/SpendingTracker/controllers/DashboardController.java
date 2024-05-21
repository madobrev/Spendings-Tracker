package apps.SpendingTracker.controllers;

import apps.SpendingTracker.services.ExpenseService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.Month;


@Controller
public class DashboardController { //TODO: Create a dashboard service?
    private final ExpenseService expenseService;
    private final HttpSession httpSession;

    public DashboardController(ExpenseService expenseService, HttpSession httpSession) {
        this.expenseService = expenseService;
        this.httpSession = httpSession;
    }

    @RequestMapping("/dashboard")
    public ModelAndView dashboard(@RequestParam(required = false, defaultValue = "0") int month, Model model) {
        String username = (String) httpSession.getAttribute("username");


        if (username == null) {
            return new ModelAndView("redirect:/home");
        }

        month = month == 0 ? LocalDate.now().getMonthValue() : month; // is it the first load (0 as param), so that the current month's spendings should be shown, or it is a result from the redirect from selecting to change the month (1-12 as param)
        httpSession.setAttribute("month", month);
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

    @PostMapping("/filterBy")
    public ModelAndView filterBy(@RequestParam String filter, @RequestParam(required = false) String ascending,
                                 @RequestParam(required = false) String descending, Model model) {


        int month = (int) httpSession.getAttribute("month");
        boolean isAscending = ascending != null;


        //TODO: change to the current user's ID instead of the hardcoded test user ID
        model.addAttribute("expenses", expenseService.filterExpenses(4L, month, filter, isAscending).orElse(null));

        String username = (String) httpSession.getAttribute("username");

        String displayMonth = Month.of(month).toString().toLowerCase();
        displayMonth = displayMonth.substring(0, 1).toUpperCase() + displayMonth.substring(1);

        model.addAttribute("month", displayMonth);
        model.addAttribute("username", username);

        return new ModelAndView("dashboard");
    }
}
