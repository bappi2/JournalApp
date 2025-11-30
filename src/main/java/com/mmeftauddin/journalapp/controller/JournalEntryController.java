package com.mmeftauddin.journalapp.controller;

import com.mmeftauddin.journalapp.entity.JournalEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/journal")
public class JournalEntryController {
    Map<String, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public ResponseEntity<Map<String, JournalEntry>> journalEntryAll() {
        if (journalEntries.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(journalEntries); // 200 OK + JSON
    }

    @PostMapping
    public ResponseEntity<JournalEntry> add(@RequestBody JournalEntry entry) {
        if (entry.getTitle() == null || entry.getContent() == null) {
            return ResponseEntity.badRequest().build(); // 400
        }

        String id = String.valueOf(journalEntries.size() + 1);
        //entry.setId(id);
        entry.setCreatedAt(Instant.now());  // set UTC now
        entry.setUpdatedAt(Instant.now());  // set UTC now

        journalEntries.put(id, entry);

        return ResponseEntity.status(201).body(entry); // 201 Created
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalEntry> getById(@PathVariable String id) {
        JournalEntry entry = journalEntries.get(id);
        if (entry == null) {
            return ResponseEntity.notFound().build(); // 404
        }
        return ResponseEntity.ok(entry); // 200
    }

    @PutMapping("/{id}")
    public ResponseEntity<JournalEntry> update(@PathVariable String id, @RequestBody JournalEntry newEntry) {
        if (!journalEntries.containsKey(id)) {
            return ResponseEntity.notFound().build(); // 404
        }
        if (newEntry.getTitle() == null || newEntry.getContent() == null) {
            return ResponseEntity.badRequest().build(); // 400
        }

        //newEntry.setId(id);
        newEntry.setCreatedAt(journalEntries.get(id).getCreatedAt());
        newEntry.setUpdatedAt(Instant.now()); // update timestamp

        journalEntries.put(id, newEntry);

        return ResponseEntity.ok(newEntry); // 200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (journalEntries.remove(id) == null) {
            return ResponseEntity.notFound().build(); // 404
        }
        return ResponseEntity.noContent().build(); // 204
    }
}
