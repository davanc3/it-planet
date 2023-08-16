package ru.vantsyn.it.planet.model.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class AnimalTypeExistException extends ResponseStatusException {
    public AnimalTypeExistException(HttpStatusCode status) {
        super(status);
    }
}
