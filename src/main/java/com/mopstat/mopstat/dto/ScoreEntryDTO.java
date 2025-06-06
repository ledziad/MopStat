package com.mopstat.mopstat.dto;

import java.time.LocalDate;

public class ScoreEntryDTO {
    private Long id;
    private Long dogId;
    private LocalDate date;
    private int score;

    public ScoreEntryDTO() {}

    public ScoreEntryDTO(Long id, Long dogId, LocalDate date, int score) {
        this.id = id;
        this.dogId = dogId;
        this.date = date;
        this.score = score;
    }

    // Gettery i settery
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDogId() {
        return dogId;
    }

    public void setDogId(Long dogId) {
        this.dogId = dogId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
