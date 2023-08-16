package ru.vantsyn.it.planet.model.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class LocationExistException extends ResponseStatusException {
    public LocationExistException(HttpStatusCode status) {
        super(status);
    }
}
