package com.mopstat.mopstat.dto;

import java.time.LocalDate;

public class DailyRecordDTO {

    private Long id;
    private LocalDate date;
    private int meals;
    private int poops;
    private int walks;
    private String moodNote;
    private Long dogId; // DODAJ TO!

    // --- Konstruktory ---
    public DailyRecordDTO() {
    }

    public DailyRecordDTO(Long id, LocalDate date, int meals, int poops, int walks, String moodNote, Long dogId) {
        this.id = id;
        this.date = date;
        this.meals = meals;
        this.poops = poops;
        this.walks = walks;
        this.moodNote = moodNote;
        this.dogId = dogId;
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

    public Long getDogId() {
        return dogId;
    }

    public void setDogId(Long dogId) {
        this.dogId = dogId;
    }
}
