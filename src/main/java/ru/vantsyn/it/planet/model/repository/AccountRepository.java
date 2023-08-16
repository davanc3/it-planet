package ru.vantsyn.it.planet.model.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vantsyn.it.planet.model.entity.Account;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    /**
     * Получение аккаунта по email
     * @param email email аккаунт
     * @return аккаунт
     */
    Account findFirstByEmail(String email);

    /**
     * Получение аккаунта по id
     * @param id id аккаунта
     * @return аккаунт
     */
    Account findFirstById(long id);

    @Query("select a from Account a where a.firstName like %:firstName% and " +
            "a.lastName like %:lastName% and a.email like %:email%")
    List<Account> searchAccounts(@Param("firstName") String firstName, @Param("lastName") String lastName,
                                 @Param("email") String email, Pageable pageable);


}
