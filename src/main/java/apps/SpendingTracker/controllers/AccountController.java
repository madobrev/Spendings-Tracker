package apps.SpendingTracker.controllers;

import apps.SpendingTracker.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model, RedirectAttributes redirectAttributes) {

        logger.info("Attempting to login as {}", username);
        boolean isLoginSuccessful = accountService.login(username, password);

        if (isLoginSuccessful) {
            return "redirect:/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("logError", "Invalid username or password!");
            return "redirect:/home";
        }
    }

    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           Model model) {
        logger.info("Attempting to register user: {}", username);
        accountService.register(username, password);
        return "redirect:/home";
    }
}
