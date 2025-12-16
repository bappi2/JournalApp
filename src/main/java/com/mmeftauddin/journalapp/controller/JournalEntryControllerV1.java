package com.mmeftauddin.journalapp.controller;

import com.mmeftauddin.journalapp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/journal")
public class JournalEntryControllerV1 {
    Map<String, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public ResponseEntity<Collection<JournalEntry>> getAllEntries() {
        // Return all journal entries stored in the in-memory map
        return new ResponseEntity<>(journalEntries.values(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> save(@RequestBody JournalEntry journalEntry) {
        String id = new ObjectId().toHexString();          // create ObjectId
        journalEntry.setId(id);                // set ObjectId on entity
        journalEntries.put(id, journalEntry); // if your map key is String
        return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
    }

    @GetMapping("/{myId}")
    public ResponseEntity<JournalEntry> getById(@PathVariable String myId) {
        ObjectId objectId = new ObjectId(myId);

        JournalEntry entry = journalEntries.get(myId);
        if (entry == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entry, HttpStatus.OK);
    }

    @PutMapping("/{myId}")
    public ResponseEntity<JournalEntry> update(@PathVariable String myId, @RequestBody JournalEntry journalEntry) {
        if (!journalEntries.containsKey(myId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        journalEntries.put(myId, journalEntry);
        return new ResponseEntity<>(journalEntry, HttpStatus.OK);
    }

    @PatchMapping("/{myId}")
    public ResponseEntity<JournalEntry> patch(@PathVariable String myId, @RequestBody JournalEntry partialEntry) {
        JournalEntry existing = journalEntries.get(myId);
        if (existing == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Apply partial updates (example for a few fields; adjust to your entity)
        if (partialEntry.getTitle() != null) {
            existing.setTitle(partialEntry.getTitle());
        }
        if (partialEntry.getContent() != null) {
            existing.setContent(partialEntry.getContent());
        }
        if (partialEntry.getMood() != null) {
            existing.setMood(partialEntry.getMood());
        }
        // Add other fields as needed
        return new ResponseEntity<>(existing, HttpStatus.OK);
    }

    @DeleteMapping("/{myId}")
    public ResponseEntity<Void> delete(@PathVariable String myId) {
        JournalEntry removed = journalEntries.remove(myId);
        if (removed == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
