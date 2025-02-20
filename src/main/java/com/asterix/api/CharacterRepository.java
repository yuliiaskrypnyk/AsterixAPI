package com.asterix.api;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CharacterRepository extends MongoRepository<CharacterRecord, String> {

}