package com.mmeftauddin.journalapp.service;

import com.mmeftauddin.journalapp.entity.JournalEntry;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface JournalEntryService {
    public void addJournalEntry(String username, JournalEntry entry);
    public List<JournalEntry> getAllEntries();
    public Optional<JournalEntry> getEntryById(String id);
    public JournalEntry updateJournalEntry(String id, JournalEntry newEntry);
    public void deleteJournalEntry(String username, String id);
    public List<JournalEntry> getAllEntriesForUser(String username);
}
