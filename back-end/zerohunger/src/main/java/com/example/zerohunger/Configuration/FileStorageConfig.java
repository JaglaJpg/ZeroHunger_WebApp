package com.example.zerohunger.Configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileStorageConfig {
    
    private final Path uploadRoot = Paths.get("uploads");
    private final Path foodImagesPath = Paths.get("uploads/food");
    
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(uploadRoot);
            Files.createDirectories(foodImagesPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage locations", e);
        }
    }
}