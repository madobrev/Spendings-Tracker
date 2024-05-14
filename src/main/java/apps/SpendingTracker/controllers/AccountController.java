package apps.SpendingTracker.controllers;

import apps.SpendingTracker.services.AccountService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private final AccountService accountService;
    private final HttpSession httpSession;

    public AccountController(AccountService accountService, HttpSession httpSession) {
        this.accountService = accountService;
        this.httpSession = httpSession;
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
                           @RequestParam("repeatPassword") String repeatPassword,
                           Model model) {
        logger.info("Attempting to register user: {}", username);

        if (!accountService.checkIfPasswordsMatch(password, repeatPassword)) {
            model.addAttribute("errorMessage", "Passwords do not match");
            return "register";
        }

        //TODO: add username check

        accountService.register(username, password);
        return "redirect:/home";
    }

    @RequestMapping("/logout")
    public ModelAndView logout() {
        httpSession.setAttribute("username", null);
        return new ModelAndView("redirect:/home");
    }

    @PostMapping("/changePassword")
    public ModelAndView changePassword(@RequestParam("oldPassword") String oldPassword,
                                       @RequestParam("newPassword") String newPassword,
                                       @RequestParam("repeatPassword") String repeatPassword,
                                       Model model) {


        if (!accountService.checkIfCurrentPasswordIsCorrect(oldPassword)) {
            model.addAttribute("errorMessage", "Incorrect old password");
            return new ModelAndView("redirect:/showChangePasswordPage");
        }

        if (!accountService.checkIfPasswordsMatch(newPassword, repeatPassword)) {
            model.addAttribute("errorMessage", "Passwords do not match");
            return new ModelAndView("redirect:/showChangePasswordPage");
        }

        accountService.changePassword(newPassword);
        return new ModelAndView("redirect:/dashboard");
    }

    @RequestMapping("/deleteAccount")
    public String deleteAccount() {
        accountService.deleteAccount();
        return "redirect:/home";
    }


}
