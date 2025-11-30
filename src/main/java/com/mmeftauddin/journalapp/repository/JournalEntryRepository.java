package com.mmeftauddin.journalapp.repository;

import com.mmeftauddin.journalapp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
    // This interface will automatically provide CRUD operations for JournalEntry
    // No need to implement methods, Spring Data MongoDB does it for us
}
