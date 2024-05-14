package apps.SpendingTracker.repositories;

import apps.SpendingTracker.models.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> findByUsername(String username);

    @Modifying
    @Query("UPDATE Account a SET a.password = :newPassword WHERE a.username = :username")
    void changePassword(String username, String newPassword);

    @Modifying
    @Query("DELETE FROM Account a WHERE a.username = :username")
    void deleteUser(String username);


}
