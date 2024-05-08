package apps.SpendingTracker.services;

import apps.SpendingTracker.models.Account;
import apps.SpendingTracker.repositories.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void login() {
        //Check if the user exists in the db and password matches.
    }

    @Transactional
    public void register(String username, String password) {
        Account account = new Account(username, password);
        accountRepository.save(account);
    }

    public void updatePassword() {

    }

    public void deleteAccount() {

    }
}
