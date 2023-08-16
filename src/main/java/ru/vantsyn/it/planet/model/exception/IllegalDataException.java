package ru.vantsyn.it.planet.model.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class IllegalDataException extends ResponseStatusException {
    public IllegalDataException(HttpStatusCode status) {
        super(status);
    }

    public IllegalDataException(HttpStatusCode status, String reason) {
        super(status, reason);
    }
}
