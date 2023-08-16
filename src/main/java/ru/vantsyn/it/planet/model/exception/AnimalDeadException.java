package ru.vantsyn.it.planet.model.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class AnimalDeadException extends ResponseStatusException {
    public AnimalDeadException(HttpStatusCode status) {
        super(status);
    }
}
