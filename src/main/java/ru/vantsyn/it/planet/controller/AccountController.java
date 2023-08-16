package ru.vantsyn.it.planet.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ru.vantsyn.it.planet.model.dto.account.AccountCreateDTO;
import ru.vantsyn.it.planet.model.dto.account.AccountResultDto;
import ru.vantsyn.it.planet.model.dto.account.AccountSearchFilterDTO;
import ru.vantsyn.it.planet.model.entity.Account;
import ru.vantsyn.it.planet.model.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    /**
     * Сервис для реализации бизнес логики работы с аккаунтами
     */
    private final AccountService accountService;
    /**
     * Класс для автоматического преобразования Entity в DTO и наоборот
     */
    private final ModelMapper modelMapper;

    /**
     * Конструктор, для реализации DI
     *
     * @param accountService экземпляр AccountService
     * @param modelMapper экземпляр ModelMapper
     */
    public AccountController(AccountService accountService, ModelMapper modelMapper) {
        this.accountService = accountService;
        this.modelMapper = modelMapper;
    }

    /**
     * Получение аккаунта по id
     *
     * @param accountId id аккаунта
     * @return аккаунт
     */
    @GetMapping("/{accountId}")
    public AccountResultDto getAccountInfo(@PathVariable int accountId) {
        return accountService.getById(accountId);
    }

    /**
     * Поиск аккаунта по заданному фильтру
     *
     * @param accountSearchFilterDTO фильтр
     * @return список аккаунтов
     */
    @GetMapping("/search")
    public List<Account> searchAccounts(@Valid AccountSearchFilterDTO accountSearchFilterDTO) {
        return accountService.searchAccounts(accountSearchFilterDTO);
    }

    /**
     * Изменение параметров аккаунта
     *
     * @param accountId id аккаунта
     * @param accountCreateDTO информация, которую необходимо изменить
     * @param request объект с данными запроса
     * @return аккаунт
     */
    @PutMapping("/{accountId}")
    public AccountResultDto updateAccountInfo(@PathVariable int accountId, @Valid @RequestBody AccountCreateDTO accountCreateDTO,
                                     HttpServletRequest request) {
        accountService.checkUserAuthInHisOwnAccount(accountId, request);
        return accountService.updateAccount(accountId, modelMapper.map(accountCreateDTO, Account.class));
    }

    /**
     * Удаление аккаунта по id
     *
     * @param accountId id аккаунт
     * @param request объект с данными запроса
     */
    @DeleteMapping("/{accountId}")
    public void deleteAccount(@PathVariable int accountId,
                                     HttpServletRequest request) {
        accountService.checkUserAuthInHisOwnAccount(accountId, request);
        accountService.deleteAccount(accountId);
    }
}
