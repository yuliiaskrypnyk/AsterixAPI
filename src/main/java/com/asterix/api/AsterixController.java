package com.asterix.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/characters")
    public CharacterRecord addCharacter(@RequestBody CharacterRecord character) {
        return repository.save(character);
    }

    @PutMapping("/characters/{id}")
    public ResponseEntity<String> updateCharacter(@PathVariable String id, @RequestBody CharacterRecord updatedCharacter) {
        return repository.findById(id)
                .map(existingCharacter -> {
                    CharacterRecord updated = new CharacterRecord(
                            existingCharacter.id(),
                            (updatedCharacter.name() != null && !updatedCharacter.name().isBlank()) ? updatedCharacter.name() : existingCharacter.name(),
                            (updatedCharacter.age() > 0) ? updatedCharacter.age() : existingCharacter.age(),
                            (updatedCharacter.profession() != null && !updatedCharacter.profession().isBlank()) ? updatedCharacter.profession() : existingCharacter.profession()
                    );
                    repository.save(updated);
                    return ResponseEntity.ok("Character updated successfully:\n" + updated);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Character with ID " + id + " not found"));
    }

    @DeleteMapping("/characters/{id}")
    public void deleteCharacter(@PathVariable String id) {
        repository.deleteById(id);
    }
}