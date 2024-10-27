package com.library.controller;

import com.library.service.LibraryService;
import com.library.exception.LibraryException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/library")
public class LibraryController {
    
    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(
            @RequestParam String studentId,
            @RequestParam String isbn) {
        try {
            libraryService.borrowBook(studentId, isbn);
            return ResponseEntity.ok("Book borrowed successfully");
        } catch (LibraryException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnBook(
            @RequestParam String studentId,
            @RequestParam String isbn) {
        try {
            libraryService.returnBook(studentId, isbn);
            return ResponseEntity.ok("Book returned successfully");
        } catch (LibraryException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}