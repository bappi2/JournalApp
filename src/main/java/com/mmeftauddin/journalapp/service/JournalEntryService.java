package com.mmeftauddin.journalapp.service;

import com.mmeftauddin.journalapp.entity.JournalEntry;
import com.mmeftauddin.journalapp.repository.JournalEntryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void addJournalEntry(JournalEntry entry) {
        // Logic to add a journal entry
        entry.setCreatedAt(Instant.now());
        entry.setUpdatedAt(Instant.now());
        journalEntryRepository.save(entry);
    }

    public List<JournalEntry> getAllEntries() {
        // Logic to retrieve all journal entries
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntryById(ObjectId id) {
        // Logic to retrieve a journal entry by ID
        return journalEntryRepository.findById(id);
    }
    public JournalEntry updateJournalEntry(ObjectId id, JournalEntry newEntry) {
        JournalEntry existingEntry = journalEntryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("JournalEntry not found: " + id));

        if (newEntry.getTitle() != null) {
            existingEntry.setTitle(newEntry.getTitle());
        }
        if (newEntry.getContent() != null) {
            existingEntry.setContent(newEntry.getContent());
        }
        if (newEntry.getMood() != null) {
            existingEntry.setMood(newEntry.getMood());
        }

        existingEntry.setUpdatedAt(Instant.now());
        return journalEntryRepository.save(existingEntry);
    }

    public void deleteJournalEntry(ObjectId id) {
        // Logic to delete a journal entry
        journalEntryRepository.deleteById(id);
    }
}
