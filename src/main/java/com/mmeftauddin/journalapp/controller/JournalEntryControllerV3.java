package com.mmeftauddin.journalapp.controller;

import com.mmeftauddin.journalapp.entity.JournalEntry;
import com.mmeftauddin.journalapp.service.JournalEntryService;
import com.mmeftauddin.journalapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v3/journal")
public class JournalEntryControllerV3 {

    private final JournalEntryService journalEntryService;
    private final UserService userService;

    public JournalEntryControllerV3(JournalEntryService journalEntryService, @Qualifier("defaultUserService") UserService userService) {
        this.journalEntryService = journalEntryService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<JournalEntry> add(@Valid @RequestBody JournalEntry entry, Authentication authentication) {
        String username = authentication.getName();
        journalEntryService.addJournalEntry(username, entry);
        return ResponseEntity.status(HttpStatus.CREATED).body(entry);
    }
    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllEntriesForUser(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(journalEntryService.getAllEntriesForUser(username));
    }

    @GetMapping("/all")
    public ResponseEntity<List<JournalEntry>> journalEntryAll() {
        return ResponseEntity.ok(journalEntryService.getAllEntries()); // 200 OK + JSON
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalEntry> getById(@PathVariable String id) {
        return ResponseEntity.of(journalEntryService.getEntryById(id)); // 200 OK or 404 Not Found
    }

    @PutMapping("/{id}")
    public ResponseEntity<JournalEntry> update(@PathVariable String id, @RequestBody JournalEntry newEntry, Authentication authentication) {

        return ResponseEntity.ok(journalEntryService.updateJournalEntry(id, newEntry)); // 200 OK
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id, Authentication authentication) {
        String username = authentication.getName();
        journalEntryService.deleteJournalEntry(username, id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

}
