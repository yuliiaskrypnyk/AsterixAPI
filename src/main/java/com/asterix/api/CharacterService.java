package com.asterix.api;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {
    private final CharacterRepository repository;

    public CharacterService(CharacterRepository repository) {
        this.repository = repository;
    }

    public CharacterRecord getCharacterById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new CharacterNotFoundException(id));
    }

    public List<CharacterRecord> findCharacters(String name, Integer age, String profession) {
        if (name != null && age != null && profession != null) {
            return repository.findByNameContainingIgnoreCaseAndAgeAndProfessionContainingIgnoreCase(name, age, profession);
        } else if (name != null && age != null) {
            return repository.findByNameContainingIgnoreCaseAndAge(name, age);
        } else if (name != null && profession != null) {
            return repository.findByNameContainingIgnoreCaseAndProfessionContainingIgnoreCase(name, profession);
        } else if (age != null && profession != null) {
            return repository.findByAgeAndProfessionContainingIgnoreCase(age, profession);
        } else if (name != null) {
            return repository.findByNameContainingIgnoreCase(name);
        } else if (age != null) {
            return repository.findByAge(age);
        } else if (profession != null) {
            return repository.findByProfessionContainingIgnoreCase(profession);
        } else {
            return repository.findAll();
        }
    }

    public CharacterRecord addCharacter(CharacterRecord character) {
        return repository.save(character);
    }

    public CharacterRecord updateCharacter(String id, CharacterRecord updatedCharacter) {
        CharacterRecord existingCharacter = repository.findById(id)
                .orElseThrow(() -> new CharacterNotFoundException(id));

        CharacterRecord updated = new CharacterRecord(
                existingCharacter.id(),
                (updatedCharacter.name() != null && !updatedCharacter.name().isBlank()) ? updatedCharacter.name() : existingCharacter.name(),
                (updatedCharacter.age() > 0) ? updatedCharacter.age() : existingCharacter.age(),
                (updatedCharacter.profession() != null && !updatedCharacter.profession().isBlank()) ? updatedCharacter.profession() : existingCharacter.profession()
        );

        return repository.save(updated);
    }

    public void deleteCharacter(String id) {
        if (!repository.existsById(id)) {
            throw new CharacterNotFoundException(id);
        }
        repository.deleteById(id);
    }

    public double getAverageAgeByProfession(String profession) {
        List<CharacterRecord> characters = repository.findByProfessionContainingIgnoreCase(profession);

        return characters.isEmpty() ? 0 : characters.stream()
                .mapToInt(CharacterRecord::age)
                .average()
                .orElse(0);
    }
}
