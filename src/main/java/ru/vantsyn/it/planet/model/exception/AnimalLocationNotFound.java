package ru.vantsyn.it.planet.model.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class AnimalLocationNotFound extends ResponseStatusException {
    public AnimalLocationNotFound(HttpStatusCode status) {
        super(status);
    }
}
