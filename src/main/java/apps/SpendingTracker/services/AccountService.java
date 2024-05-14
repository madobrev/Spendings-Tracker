package apps.SpendingTracker.services;

import apps.SpendingTracker.models.Account;
import apps.SpendingTracker.repositories.AccountRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepository;
    private final HttpSession httpSession;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AccountService(AccountRepository accountRepository, HttpSession httpSession) {
        this.accountRepository = accountRepository;
        this.httpSession = httpSession;
    }

    public boolean login(String username, String password) {
        Optional<Account> optionalAccount = accountRepository.findByUsername(username);
        if (optionalAccount.isPresent()) {
            httpSession.setAttribute("username", username);
            Account account = optionalAccount.get();
            return passwordEncoder.matches(password, account.getPassword());
        } else {
            return false;
        }

    }

    public Optional<Account> getCurrentUser() {
        String username = (String) httpSession.getAttribute("username");
        if (username != null) {
            return accountRepository.findByUsername(username);
        }
        return Optional.empty();
    }

    @Transactional
    public void register(String username, String password) {
        String hashedPassword = passwordEncoder.encode(password);

        Account account = new Account(username, hashedPassword);
        accountRepository.save(account);
    }


    public void updatePassword() {

    }

    public void deleteAccount() {

    }
}
