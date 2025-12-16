package com.mmeftauddin.journalapp.service;

import com.mmeftauddin.journalapp.entity.JournalEntry;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface JournalEntryService {
    public void addJournalEntry(JournalEntry entry);
    public List<JournalEntry> getAllEntries();
    public Optional<JournalEntry> getEntryById(ObjectId id);
    public JournalEntry updateJournalEntry(ObjectId id, JournalEntry newEntry);
    public void deleteJournalEntry(ObjectId id);
}
