package com.mmeftauddin.journalapp.controller;

import com.mmeftauddin.journalapp.entity.JournalEntry;
import com.mmeftauddin.journalapp.repository.JournalEntryRepository;
import com.mmeftauddin.journalapp.service.JournalEntryService;
import com.mmeftauddin.journalapp.service.UserService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/journal")
public class JournalEntryControllerV2 {

    private final JournalEntryService journalEntryService;
    private final UserService userService;

    public JournalEntryControllerV2(JournalEntryService journalEntryService, UserService userService) {
        this.journalEntryService = journalEntryService;
        this.userService = userService;
    }

    @PostMapping("{username}")
    public ResponseEntity<JournalEntry> add(@PathVariable String username, @Valid @RequestBody JournalEntry entry) {
        journalEntryService.addJournalEntry(username, entry);
        return ResponseEntity.status(HttpStatus.CREATED).body(entry);
    }
    @GetMapping("/user/{username}")
    public ResponseEntity<List<JournalEntry>> getAllEntriesForUser(@PathVariable String username) {
        return ResponseEntity.ok(journalEntryService.getAllEntriesForUser(username));
    }

    @GetMapping
    public ResponseEntity<List<JournalEntry>> journalEntryAll() {
        return ResponseEntity.ok(journalEntryService.getAllEntries()); // 200 OK + JSON
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalEntry> getById(@PathVariable String id) {
        return ResponseEntity.of(journalEntryService.getEntryById(id)); // 200 OK or 404 Not Found
    }

    @PutMapping("/{id}")
    public ResponseEntity<JournalEntry> update(@PathVariable String id, @RequestBody JournalEntry newEntry) {

        return ResponseEntity.ok(journalEntryService.updateJournalEntry(id, newEntry)); // 200 OK
    }

    @DeleteMapping("/user/{user}/id/{id}")
    public ResponseEntity<Void> delete(@PathVariable String user, @PathVariable String id) {
        journalEntryService.deleteJournalEntry(user, id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

}
