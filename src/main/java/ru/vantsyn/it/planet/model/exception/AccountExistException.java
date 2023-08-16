package ru.vantsyn.it.planet.model.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class AccountExistException extends ResponseStatusException {
    public AccountExistException(HttpStatusCode status) {
        super(status);
    }

    public AccountExistException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
