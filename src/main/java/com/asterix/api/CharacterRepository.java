package com.asterix.api;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CharacterRepository extends MongoRepository<CharacterRecord, String> {
    List<CharacterRecord> findByNameContainingIgnoreCase(String name);
    List<CharacterRecord> findByAge(Integer age);
    List<CharacterRecord> findByProfessionContainingIgnoreCase(String profession);
    List<CharacterRecord> findByNameContainingIgnoreCaseAndAge(String name, Integer age);
    List<CharacterRecord> findByNameContainingIgnoreCaseAndProfessionContainingIgnoreCase(String name, String profession);
    List<CharacterRecord> findByAgeAndProfessionContainingIgnoreCase(Integer age, String profession);
    List<CharacterRecord> findByNameContainingIgnoreCaseAndAgeAndProfessionContainingIgnoreCase(String name, Integer age, String profession);
}