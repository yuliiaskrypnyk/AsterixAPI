package com.asterix.api;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CharacterRepository extends MongoRepository<CharacterRecord, String> {
    List<CharacterRecord> findByNameContainingAndAgeAndProfession(String name, Integer age, String profession);

    List<CharacterRecord> findByNameContainingAndAge(String name, Integer age);

    List<CharacterRecord> findByNameContainingAndProfession(String name, String profession);

    List<CharacterRecord> findByAgeAndProfession(Integer age, String profession);

    List<CharacterRecord> findByNameContaining(String name);

    List<CharacterRecord> findByAge(Integer age);

    List<CharacterRecord> findByProfessionContaining(String profession);
}