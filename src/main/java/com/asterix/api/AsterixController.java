package com.asterix.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/asterix")
public class AsterixController {
    private final CharacterService characterService;

    public AsterixController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("/characters/{id}")
    public CharacterRecord getCharacterById(@PathVariable String id) {
        return characterService.getCharacterById(id);
    }

    @GetMapping("/characters")
    public List<CharacterRecord> getCharacters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String profession) {
        return characterService.findCharacters(name, age, profession);
    }

    @PostMapping("/characters")
    public ResponseEntity<CharacterRecord> addCharacter(@RequestBody CharacterRecord character) {
        CharacterRecord savedCharacter = characterService.addCharacter(character);
        return ResponseEntity.ok(savedCharacter);
    }

    @PutMapping("/characters/{id}")
    public ResponseEntity<CharacterRecord> updateCharacter(@PathVariable String id, @RequestBody CharacterRecord updatedCharacter) {
        CharacterRecord updated = characterService.updateCharacter(id, updatedCharacter);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/characters/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable String id) {
        characterService.deleteCharacter(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/characters/average-age")
    public double getAverageAgeByProfession(@RequestParam String profession) {
        return characterService.getAverageAgeByProfession(profession);
    }
}