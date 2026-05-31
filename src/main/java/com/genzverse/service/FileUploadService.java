package com.genzverse.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

@Service
public class FileUploadService
{
    private final Cloudinary cloudinary;

    public FileUploadService(
            Cloudinary cloudinary)
    {
        this.cloudinary = cloudinary;
    }

    public String uploadFile(
            MultipartFile file)
    {
        try
        {
            Map<?, ?> result =
                    cloudinary.uploader().upload(
                            file.getBytes(),
                            Map.of()
                    );

            return result
                    .get("secure_url")
                    .toString();
        }
        catch(IOException e)
        {
            throw new RuntimeException(
                    "File upload failed"
            );
        }
    }
}