package apps.SpendingTracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebPageController {

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }


    @GetMapping("/new_expense")
    public String showNewExpenseForm(Model model) {
        return "new_expense";
    }

    @RequestMapping("/showDeleteAccountPage")
    public String deleteAccount() {
        return "delete_account";
    }

    @RequestMapping("/showChangePasswordPage")
    public String changePassword() {
        return "change_password";
    }
}

