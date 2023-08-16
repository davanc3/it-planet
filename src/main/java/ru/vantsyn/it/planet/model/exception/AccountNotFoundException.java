package ru.vantsyn.it.planet.model.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class AccountNotFoundException extends ResponseStatusException {
    public AccountNotFoundException(HttpStatusCode status) {
        super(status);
    }

    public AccountNotFoundException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
