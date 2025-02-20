package com.asterix.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CharacterNotFoundException extends RuntimeException {
    public CharacterNotFoundException(String id) {
        super("Character not found with ID: " + id);
    }
}
