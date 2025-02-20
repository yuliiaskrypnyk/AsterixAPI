package com.asterix.api;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "character")
public record CharacterRecord(@Id String id, String name, int age, String profession) {
}