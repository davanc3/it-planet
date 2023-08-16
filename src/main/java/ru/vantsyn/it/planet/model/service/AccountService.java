package ru.vantsyn.it.planet.model.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.vantsyn.it.planet.model.dto.account.AccountResultDto;
import ru.vantsyn.it.planet.model.dto.account.AccountSearchFilterDTO;
import ru.vantsyn.it.planet.model.entity.Account;

import java.util.List;

public interface AccountService {
    AccountResultDto getById(int id);
    boolean isAuthorizeValid(String basicAuth);
    AccountResultDto createAccount(Account account);
    List<Account> searchAccounts(AccountSearchFilterDTO accountSearchFilterDTO);
    AccountResultDto updateAccount(int id, Account newAccount);
    void checkUserAuthInHisOwnAccount(int id, HttpServletRequest request);
    String checkIsAuthorize(HttpServletRequest request);
    void deleteAccount(int id);
    Account convertAccountResultDtoToAccount(AccountResultDto accountResultDto);
}
