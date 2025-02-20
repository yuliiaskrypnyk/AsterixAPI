package com.asterix.api;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("character")
public record CharacterRecord(@Id String id, String name, int age, String profession) {
}