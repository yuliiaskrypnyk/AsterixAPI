package com.asterix.api;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CharacterServiceTest {
    CharacterRepository mockcharacterRepository = mock(CharacterRepository.class);
    CharacterService characterService = new CharacterService(mockcharacterRepository);

    @Test
    void testGetCharacterById() {
        CharacterRecord mockCharacter = new CharacterRecord("1", "Asterix", 35, "Warrior");
        when(mockcharacterRepository.findById("1")).thenReturn(Optional.of(mockCharacter));

        CharacterRecord result = characterService.getCharacterById("1");
        assertNotNull(result);
        assertEquals("Asterix", result.name());
    }

    @Test
    void findCharacters() {
        CharacterRecord character1 = new CharacterRecord("1", "Asterix", 35, "Warrior");
        CharacterRecord character2 = new CharacterRecord("2", "Obelix", 40, "Stonecutter");
        when(mockcharacterRepository.findAll()).thenReturn(List.of(character1, character2));

        List<CharacterRecord> result = characterService.findCharacters(null, null, null);
        assertEquals(2, result.size());
        assertTrue(result.contains(character1));
        assertTrue(result.contains(character2));
    }

    @Test
    void updateCharacter() {
        CharacterRecord existingCharacter = new CharacterRecord("1", "Asterix", 35, "Warrior");
        CharacterRecord updatedCharacter = new CharacterRecord("1", "Asterix the Brave", 36, "Hero");

        when(mockcharacterRepository.findById("1")).thenReturn(Optional.of(existingCharacter));
        when(mockcharacterRepository.save(any(CharacterRecord.class))).thenReturn(updatedCharacter);

        CharacterRecord result = characterService.updateCharacter("1", updatedCharacter);
        assertEquals("Asterix the Brave", result.name());
        assertEquals(36, result.age());
        assertEquals("Hero", result.profession());
    }

    @Test
    void testDeleteCharacter() {
        String id = "1";
        when(mockcharacterRepository.existsById(id)).thenReturn(true);
        doNothing().when(mockcharacterRepository).deleteById(id);

        assertDoesNotThrow(() -> characterService.deleteCharacter(id));
    }

    @Test
    void testDeleteCharacter_NotFound() {
        String id = "2";
        when(mockcharacterRepository.existsById(id)).thenReturn(false);

        assertThrows(CharacterNotFoundException.class, () -> characterService.deleteCharacter(id));
    }
}