package com.mmeftauddin.journalapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health-check")
public class HealthCheck {

    @GetMapping()
    public ResponseEntity health() {
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity admin() {
        return ResponseEntity.ok("Hello from admin");
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/users")
    public ResponseEntity normal() {
        return ResponseEntity.ok("Hello from users");
    }

}
