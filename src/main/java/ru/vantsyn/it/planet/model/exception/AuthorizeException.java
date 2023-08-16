package ru.vantsyn.it.planet.model.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class AuthorizeException extends ResponseStatusException {
    public AuthorizeException(HttpStatusCode status) {
        super(status);
    }

    public AuthorizeException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
