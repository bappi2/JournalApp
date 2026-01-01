package com.mmeftauddin.journalapp.service;

import com.mmeftauddin.journalapp.entity.JournalEntry;
import com.mmeftauddin.journalapp.entity.User;
import com.mmeftauddin.journalapp.repository.JournalEntryRepository;
import com.mmeftauddin.journalapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultJournalEntryService implements JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;
    private final UserRepository userRepository;

    public DefaultJournalEntryService(JournalEntryRepository journalEntryRepository, UserRepository userRepository) {
        this.journalEntryRepository = journalEntryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<JournalEntry> getAllEntriesForUser(String username){
        Optional<User> existingUser =  userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            return existingUser.get().getJournalEntries();
        }
        return null;
    }

    @Transactional
    @Override
    public void addJournalEntry(String username, JournalEntry entry) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        entry.setCreatedAt(Instant.now());
        entry.setUpdatedAt(Instant.now());

        JournalEntry saved = journalEntryRepository.save(entry);

        user.getJournalEntries().add(saved);
        //user.setPassword(null);
        userRepository.save(user);

        // Force failure AFTER both writes
        //throw new RuntimeException("Force rollback test");
    }
    @Override
    public List<JournalEntry> getAllEntries() {
        // Logic to retrieve all journal entries
        return journalEntryRepository.findAll();
    }
    @Override
    public Optional<JournalEntry> getEntryById(String id) {
        // Logic to retrieve a journal entry by ID
        return journalEntryRepository.findById(id);
    }
    @Override
    public JournalEntry updateJournalEntry(String id, JournalEntry newEntry) {
        String username = currentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AccessDeniedException("User not found: " + username));

        boolean owns = user.getJournalEntries().stream()
                .anyMatch(e -> e != null && id.equals(e.getId()));

        if (!owns) {
            throw new AccessDeniedException("You can only update your own journal entries");
        }
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
    @Override
    public void deleteJournalEntry(String username, String id) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        userOpt.ifPresent(user -> {
            user.getJournalEntries().removeIf(j -> j.getId().equals(id));
            userRepository.save(user);
        });
        // Logic to delete a journal entry
        journalEntryRepository.deleteById(id);
    }
    private String currentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            throw new AccessDeniedException("Not authenticated");
        }
        return auth.getName();
    }
}
