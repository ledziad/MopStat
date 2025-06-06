package com.mopstat.mopstat.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class ScoreEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dog_id")
    private Dog dog;

    private LocalDate date;

    private int score;

    public ScoreEntry() {}

    public ScoreEntry(Dog dog, LocalDate date, int score) {
        this.dog = dog;
        this.date = date;
        this.score = score;
    }

    // Gettery i settery
    public Long getId() {
        return id;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
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
