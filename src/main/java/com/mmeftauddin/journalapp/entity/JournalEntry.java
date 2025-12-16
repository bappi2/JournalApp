// src/main/java/com/mmeftauddin/journalapp/entity/JournalEntry.java
package com.mmeftauddin.journalapp.entity;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "journal_entries") // Optional, defaults to class name
@Data
public class JournalEntry {

    @Id
    private String id;
    // Mongo will generate ObjectId; Spring stores it as hex string
    @NotBlank(message = "Title is required")
    private String title;

    private String content;

    private String mood;

    @CreatedDate
    private Instant createdAt; // stored as BSON Date (UTC)

    @LastModifiedDate
    private Instant updatedAt;

}
