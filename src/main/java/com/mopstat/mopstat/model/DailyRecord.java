package com.mopstat.mopstat.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class DailyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private int meals;
    private int poops;
    private int walks;
    private String moodNote;

    @ManyToOne
    @JoinColumn(name = "dog_id")
    private Dog dog;

    // --- Konstruktory ---
    public DailyRecord() {
    }

    public DailyRecord(Long id, LocalDate date, int meals, int poops, int walks, String moodNote, Dog dog) {
        this.id = id;
        this.date = date;
        this.meals = meals;
        this.poops = poops;
        this.walks = walks;
        this.moodNote = moodNote;
        this.dog = dog;
    }

    // --- Gettery i Settery ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public int getMeals() { return meals; }
    public void setMeals(int meals) { this.meals = meals; }

    public int getPoops() { return poops; }
    public void setPoops(int poops) { this.poops = poops; }

    public int getWalks() { return walks; }
    public void setWalks(int walks) { this.walks = walks; }

    public String getMoodNote() { return moodNote; }
    public void setMoodNote(String moodNote) { this.moodNote = moodNote; }

    public Dog getDog() { return dog; }
    public void setDog(Dog dog) { this.dog = dog; }
}
