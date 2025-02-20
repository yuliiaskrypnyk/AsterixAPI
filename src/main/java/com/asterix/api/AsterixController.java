package com.asterix.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/asterix")
public class AsterixController {
    private final CharacterRepository repository;

    public AsterixController(CharacterRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/characters")
    public List<CharacterRecord> getCharacters() {
        return repository.findAll();
    }

/*    @GetMapping("/characters")
    public List<CharacterRecord> getCharacters() {
        return List.of(
                new CharacterRecord("1", "Asterix", 35, "Warrior"),
                new CharacterRecord("2", "Obelix", 35, "Supplier"),
                new CharacterRecord("3", "Miraculix", 60, "Druid"),
                new CharacterRecord("4", "Majestix", 60, "Chief"),
                new CharacterRecord("5", "Troubadix", 25, "Bard"),
                new CharacterRecord("6", "Gutemine", 35, "Chiefs Wife"),
                new CharacterRecord("7", "Idefix", 5, "Dog"),
                new CharacterRecord("8", "Geriatrix", 70, "Retiree"),
                new CharacterRecord("9", "Automatix", 35, "Smith"),
                new CharacterRecord("10", "Grockelix", 35, "Fisherman")
        );
    }*/
}