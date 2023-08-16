package ru.vantsyn.it.planet.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vantsyn.it.planet.model.dto.account.AccountCreateDTO;
import ru.vantsyn.it.planet.model.dto.account.AccountResultDto;
import ru.vantsyn.it.planet.model.entity.Account;
import ru.vantsyn.it.planet.model.service.AccountService;

@RestController
public class AuthController {
    /**
     * Сервис для реализации бизнес логики работы с аккаунтами
     */
    private final AccountService accountService;
    /**
     * Класс для автоматического преобразования Entity в DTO и наоборот
     */
    private final ModelMapper mapper;

    /**
     * Конструктор, для реализации DI
     *
     * @param accountService экземпляр AccountService
     * @param mapper экземпляр ModelMapper
     */
    public AuthController(AccountService accountService, ModelMapper mapper) {
        this.accountService = accountService;
        this.mapper = mapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<AccountResultDto> createAccount(@Valid @RequestBody AccountCreateDTO accountCreateDTO) {
        return new ResponseEntity<>(accountService.createAccount(mapper.map(accountCreateDTO, Account.class)), HttpStatus.CREATED);
    }
}
