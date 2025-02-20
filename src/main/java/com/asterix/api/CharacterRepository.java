package com.asterix.api;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CharacterRepository extends MongoRepository<CharacterRecord, String> {
    List<CharacterRecord> findByNameContainingAndAgeAndProfession(String name, Integer age, String profession);

    List<CharacterRecord> findByNameContainingAndAge(String name, Integer age);

    List<CharacterRecord> findByNameContainingAndProfession(String name, String profession);

    List<CharacterRecord> findByAgeAndProfession(Integer age, String profession);

    List<CharacterRecord> findByNameContaining(String name);

    List<CharacterRecord> findByAge(Integer age);

    List<CharacterRecord> findByProfessionContaining(String profession);

/*    @Query("{ 'profession' : ?0 }")
    double findAverageAgeByProfession(String profession);*/

    @Query(value = "{'profession': ?0}", fields = "{'age': 1}")
    List<CharacterRecord> findByProfession(String profession);
}