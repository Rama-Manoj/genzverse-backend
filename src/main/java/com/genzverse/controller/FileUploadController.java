package com.genzverse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.genzverse.service.FileUploadService;

@RestController
@RequestMapping("/api/files")
public class FileUploadController 
{
    private final FileUploadService fileUploadService;

    public FileUploadController(
            FileUploadService fileUploadService)
    {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file")
            MultipartFile file)
    {
        String fileName =
                fileUploadService.uploadFile(file);

        return ResponseEntity.ok(
                "File uploaded: " + fileName
        );
    }
}