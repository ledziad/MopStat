package com.mopstat.mopstat.dto;

public class DogDTO {
    private Long id;
    private String name;
    private String personality;
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
