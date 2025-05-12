package com.mopstat.mopstat.model;

import jakarta.persistence.*;

@Entity
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String personality;
    private String imagePath;

    public Dog() {
    }

    public Dog(String name, String personality, String imagePath) {
        this.name = name;
        this.personality = personality;
        this.imagePath = imagePath;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPersonality() {
        return personality;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
