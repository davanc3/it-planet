package ru.vantsyn.it.planet.model.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class AnimalTypeNotFoundException extends ResponseStatusException {
    public AnimalTypeNotFoundException(HttpStatusCode status) {
        super(status);
    }
}
