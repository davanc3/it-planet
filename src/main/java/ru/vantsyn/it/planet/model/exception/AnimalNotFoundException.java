package ru.vantsyn.it.planet.model.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class AnimalNotFoundException extends ResponseStatusException {
    public AnimalNotFoundException(HttpStatusCode status) {
        super(status);
    }
}
