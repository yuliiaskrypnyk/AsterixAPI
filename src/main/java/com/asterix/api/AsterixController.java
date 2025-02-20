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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/asterix")
public class AsterixController {
    private final CharacterRepository repository;

    public AsterixController(CharacterRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/characters/{id}")
    public CharacterRecord getCharacterById(@PathVariable String id) {
        Optional<CharacterRecord> character = repository.findById(id);
        if (character.isPresent()) {
            return character.get();
        } else {
            throw new CharacterNotFoundException(id);
        }
        //return character.orElseThrow(() -> new CharacterNotFoundException(id));
/*        return repository.findById(id)
                .orElseThrow(() -> new CharacterNotFoundException(id));*/
    }

    @GetMapping("/characters")
    public List<CharacterRecord> getCharacters(@RequestParam(required = false) String name,
                                               @RequestParam(required = false) Integer age,
                                               @RequestParam(required = false) String profession) {

        if (name != null && age != null && profession != null) {
            return repository.findByNameContainingAndAgeAndProfession(name, age, profession);
        } else if (name != null && age != null) {
            return repository.findByNameContainingAndAge(name, age);
        } else if (name != null && profession != null) {
            return repository.findByNameContainingAndProfession(name, profession);
        } else if (age != null && profession != null) {
            return repository.findByAgeAndProfession(age, profession);
        } else if (name != null) {
            return repository.findByNameContaining(name);
        } else if (age != null) {
            return repository.findByAge(age);
        } else if (profession != null) {
            return repository.findByProfessionContaining(profession);
        } else {
            return repository.findAll();
        }
    }

    @PostMapping("/characters")
    public CharacterRecord addCharacter(@RequestBody CharacterRecord character) {
        return repository.save(character);
    }

    @PutMapping("/characters/{id}")
    public ResponseEntity<String> updateCharacter(@PathVariable String id, @RequestBody CharacterRecord updatedCharacter) {
/*        CharacterRecord existingCharacter = repository.findById(id)
                .orElseThrow(() -> new CharacterNotFoundException(id));

        CharacterRecord updated = new CharacterRecord(
                existingCharacter.id(),
                (updatedCharacter.name() != null && !updatedCharacter.name().isBlank()) ? updatedCharacter.name() : existingCharacter.name(),
                (updatedCharacter.age() > 0) ? updatedCharacter.age() : existingCharacter.age(),
                (updatedCharacter.profession() != null && !updatedCharacter.profession().isBlank()) ? updatedCharacter.profession() : existingCharacter.profession()
        );

        repository.save(updated);
        return ResponseEntity.ok("Character updated successfully:\n" + updated);*/

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

/*    @GetMapping("/characters/average-age")
    public double getAverageAgeByProfession(@RequestParam String profession) {
        return repository.findAverageAgeByProfession(profession);
    }*/

/*    @GetMapping("/characters/average-age")
    public double getAverageAgeByProfession(@RequestParam String profession) {
        List<CharacterRecord> characters = repository.findByProfession(profession);

        if (characters.isEmpty()) {
            return 0;
        }

        double totalAge = characters.stream()
                .mapToInt(CharacterRecord::age)
                .sum();

        return totalAge / characters.size();
    }*/

    @GetMapping("/characters/average-age")
    public double getAverageAgeByProfession(@RequestParam String profession) {
        List<CharacterRecord> characters = repository.findByProfession(profession);

        if (characters.isEmpty()) {
            return 0; // Можно выбросить исключение, если это не ожидаемо
        }

        return characters.stream()
                .mapToInt(CharacterRecord::age)
                .average()
                .orElse(0);
    }
}