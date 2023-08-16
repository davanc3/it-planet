package ru.vantsyn.it.planet.model.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.vantsyn.it.planet.model.dto.account.AccountResultDto;
import ru.vantsyn.it.planet.model.dto.account.AccountSearchFilterDTO;
import ru.vantsyn.it.planet.model.entity.Account;
import ru.vantsyn.it.planet.model.exception.AccountExistException;
import ru.vantsyn.it.planet.model.exception.AccountNotFoundException;
import ru.vantsyn.it.planet.model.exception.AuthorizeException;
import ru.vantsyn.it.planet.model.exception.IllegalDataException;
import ru.vantsyn.it.planet.model.repository.AccountRepository;
import ru.vantsyn.it.planet.model.repository.RoleRepository;
import ru.vantsyn.it.planet.model.service.AccountService;
import ru.vantsyn.it.planet.model.utils.ChunkRequest;

import java.util.Base64;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    /**
     * Репозиторий для взаимодействия с таблицей account из БД
     */
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    private final ModelMapper mapper;

    /**
     * Конструктор, для реализации DI
     *
     * @param accountRepository экземпляр AccountRepository
     * @param roleRepository    экземпляр RoleRepository
     * @param mapper            объект, который реализовывает преобразование DTO к Entity и наоборот
     */
    public AccountServiceImpl(AccountRepository accountRepository, RoleRepository roleRepository, ModelMapper mapper) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    /**
     * Метод получает данные пользователя по id
     *
     * @param id id пользователя
     * @return данные пользователя
     */
    @Override
    public AccountResultDto getById(int id) {
        checkCorrectId(id);
        Account account = accountRepository.findFirstById(id);
        if (account == null) {
            throw new AccountNotFoundException(HttpStatus.NOT_FOUND, "Аккаунт с таким accountId не найден");
        }
        return mapper.map(account, AccountResultDto.class);
    }

    /**
     * Проверка на валидность авторизационных данных
     *
     * @param basicAuth авторизационные данные
     * @return результат проверки
     */
    @Override
    public boolean isAuthorizeValid(String basicAuth) {
        boolean valid = false;

        if (basicAuth.startsWith("Basic ")) {
            String authData = basicAuth.split(" ")[1];
            byte[] decodedBytes = Base64.getDecoder().decode(authData);
            String decodedAuthData = new String(decodedBytes);
            String[] loginAndPassword = decodedAuthData.split(":");
            Account account = accountRepository.findFirstByEmail(loginAndPassword[0]);
            if (account != null) {
                String password = account.getPassword();
                account.setPassword(loginAndPassword[1]);
                account.hashPassword();
                if (password.equals(account.getPassword())) {
                    valid = true;
                }
            }
        }

        return valid;
    }

    /**
     * Создание (регистрация) аккаунтов
     *
     * @param account аккаунт, который надо зарегестирировать
     * @return данные о зарегестрированном аккаунте
     */
    @Override
    public AccountResultDto createAccount(Account account) {
        if (accountRepository.findFirstByEmail(account.getEmail()) != null) {
            throw new AccountExistException(HttpStatus.CONFLICT, "Аккаунт с таким email уже существует");
        }
        account.hashPassword();
        account.setRole(roleRepository.findFirstByName("USER"));
        accountRepository.save(account);

        return mapper.map(account, AccountResultDto.class);
    }

    /**
     * Поиск аккаунтов по различным параметрам (firstName, lastName, email), с различными сдвигами и
     * максимальным количеством выборки
     *
     * @param accountSearchFilterDTO параметры поиска
     * @return результат поиска
     */
    @Override
    public List<Account> searchAccounts(AccountSearchFilterDTO accountSearchFilterDTO) {
        ChunkRequest chunkRequest = new ChunkRequest(accountSearchFilterDTO.getFrom(), accountSearchFilterDTO.getSize(),
                Sort.by(Sort.Direction.ASC, "id"));

        return accountRepository.searchAccounts(accountSearchFilterDTO.getFirstName(),
                accountSearchFilterDTO.getLastName(), accountSearchFilterDTO.getEmail(), chunkRequest);
    }

    /**
     * Обновление данных аккаунта
     *
     * @param id id аккаунта, который необходимо обновить
     * @param newAccount обновлённые данные аккаунта
     * @return аккаунт, с обновлёнными данными
     */
    @Override
    public AccountResultDto updateAccount(int id, Account newAccount) {
        Account account = accountRepository.findFirstById(id);
        if (accountRepository.findFirstByEmail(newAccount.getEmail()) != null &&
                !account.equals(accountRepository.findFirstByEmail(newAccount.getEmail()))) {
            throw new AccountExistException(HttpStatus.CONFLICT);
        }
        newAccount.setId(id);
        newAccount.hashPassword();
        accountRepository.save(newAccount);

        return mapper.map(newAccount, AccountResultDto.class);
    }

    /**
     * Проверка на корректность авторизации, а также же,
     * соответствует авторизованный аккаунт id переданного в запросе аккаунта
     *
     * @param id id переданного в запросе аккаунта
     * @param request экземпляр HttpServletRequest
     */
    @Override
    public void checkUserAuthInHisOwnAccount(int id, HttpServletRequest request) {
        checkCorrectId(id);
        Account account = accountRepository.findFirstById(id);
        String basicAuth = checkIsAuthorize(request);
        String authData = basicAuth.split(" ")[1];
        byte[] decodedBytes = Base64.getDecoder().decode(authData);
        String decodedAuthData = new String(decodedBytes);
        String login = decodedAuthData.split(":")[0];

        Account user = getAccountByBasicAuth(checkIsAuthorize(request));
        if (account == null) {
            if (user.isAdmin()) {
                throw new AuthorizeException(HttpStatus.NOT_FOUND);
            } else {
                throw new AuthorizeException(HttpStatus.FORBIDDEN);
            }
        }
        if (!account.getEmail().equals(login) && !user.isAdmin()) {
            throw new AuthorizeException(HttpStatus.FORBIDDEN);
        }

    }

    /**
     * Проверка на то, авторизован ли пользователь
     *
     * @param request экземпляр HttpServletRequest
     * @return basic авторизация
     */
    public String checkIsAuthorize(HttpServletRequest request) {
        String basicAuth = request.getHeader("Authorization");
        if (basicAuth == null) {
            throw new AuthorizeException(HttpStatus.UNAUTHORIZED);
        }

        return basicAuth;
    }

    /**
     * Получение аккаунта пользователя по
     *
     * @param basicAuth
     * @return
     */
    public Account getAccountByBasicAuth(String basicAuth) {
        String authData = basicAuth.split(" ")[1];
        byte[] decodedBytes = Base64.getDecoder().decode(authData);
        String decodedAuthData = new String(decodedBytes);
        String login = decodedAuthData.split(":")[0];

        return accountRepository.findFirstByEmail(login);
    }

    /**
     * Удаление аккаунта из БД
     *
     * @param id id аккаунта
     */
    @Override
    public void deleteAccount(int id) {
        checkCorrectId(id);
        Account account = accountRepository.findFirstById(id);
        if (!account.getAnimals().isEmpty()) {
            throw new IllegalDataException(HttpStatus.BAD_REQUEST);
        }
        accountRepository.delete(account);
    }

    /**
     * Конвертирует AccountDtoResult в Account путём подзапроса аккаунта по id (так как в dto нет информации о пароле
     * и в некоторых ситуациях возможно данная ситуация может пригодиться)
     *
     * @param accountResultDto экзмепляр AccountDtoResult
     * @return полученный в результате подзапроса аккаунт
     */
    @Override
    public Account convertAccountResultDtoToAccount(AccountResultDto accountResultDto) {
        return accountRepository.findFirstById(accountResultDto.getId());
    }

    private void checkCorrectId(int id) {
        if (id <= 0) {
            throw new IllegalDataException(HttpStatus.BAD_REQUEST);
        }
    }
}
