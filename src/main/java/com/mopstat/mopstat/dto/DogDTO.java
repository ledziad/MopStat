package com.mopstat.mopstat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;  // opcjonalnie

public class DogDTO {
    private Long id;
    @NotBlank(message = "Nazwa psa nie może być pusta")
    @Size(max = 100, message = "Nazwa może mieć maksymalnie 100 znaków")

    private String name;
    @NotBlank(message = "Charakter psa nie może być pusty")
    private String personality;
    @NotBlank(message = "Ścieżka obrazka jest wymagana")
    private String imagePath;

    public DogDTO() {}

    public DogDTO(Long id, String name, String personality, String imagePath) {
        this.id = id;
        this.name = name;
        this.personality = personality;
        this.imagePath = imagePath;
    }

    // Gettery i settery
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPersonality() { return personality; }
    public void setPersonality(String personality) { this.personality = personality; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}
