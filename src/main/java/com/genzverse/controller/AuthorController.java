package com.genzverse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.genzverse.dto.AuthorProfileResponse;
import com.genzverse.service.AuthorService;

@RestController
@RequestMapping("/api/authors")
public class AuthorController
{
    private final AuthorService authorService;

    public AuthorController(
            AuthorService authorService)
    {
        this.authorService = authorService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<AuthorProfileResponse>
    getAuthor(
            @PathVariable Long userId)
    {
        return ResponseEntity.ok(
                authorService.getAuthor(userId)
        );
    }
}