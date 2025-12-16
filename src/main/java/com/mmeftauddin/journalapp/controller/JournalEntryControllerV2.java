package com.mmeftauddin.journalapp.controller;

import com.mmeftauddin.journalapp.entity.JournalEntry;
import com.mmeftauddin.journalapp.service.JournalEntryService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> journalEntryAll() {
        return ResponseEntity.ok(journalEntryService.getAllEntries()); // 200 OK + JSON
    }

    @PostMapping
    public ResponseEntity<JournalEntry> add(@Valid @RequestBody JournalEntry entry) {
        journalEntryService.addJournalEntry(entry);
        return ResponseEntity.status(HttpStatus.CREATED).body(entry);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId id) {
        return ResponseEntity.of(journalEntryService.getEntryById(id)); // 200 OK or 404 Not Found
    }

    @PutMapping("/{id}")
    public ResponseEntity<JournalEntry> update(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        journalEntryService.updateJournalEntry(id, newEntry);
        return ResponseEntity.ok(newEntry); // 200 OK
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        journalEntryService.deleteJournalEntry(new ObjectId(id));
        return ResponseEntity.noContent().build(); // 204 No Content
    }

}
