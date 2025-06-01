package com.mopstat.mopstat.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.mopstat.mopstat.model.Dog;
import com.mopstat.mopstat.model.DailyRecord;
import com.mopstat.mopstat.model.ScoreEntry;
import com.mopstat.mopstat.repository.ScoreEntryRepository;
import com.mopstat.mopstat.repository.DogRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ScoreEntryService {

    private final ScoreEntryRepository scoreEntryRepo;
    private final DogRepository dogRepo;

    @Autowired
    private ScoreEntryRepository scoreEntryRepository;

    @Autowired
    public ScoreEntryService(ScoreEntryRepository scoreEntryRepo, DogRepository dogRepo) {
        this.scoreEntryRepo = scoreEntryRepo;
        this.dogRepo = dogRepo;
    }

    // Wylicza punkty na podstawie rekordu dziennego (możesz zmienić reguły!)
    public int calculateScore(DailyRecord record) {
        int score = 0;
        score += record.getMeals() * 2;
        score += record.getPoops() * 2;
        score += record.getWalks() * 3;
        if (record.getMoodNote() != null && !record.getMoodNote().isEmpty()) {
            score += 1;
        }
        return score;
    }

    // Tworzy lub aktualizuje punktację dla danego psa i dnia
    public ScoreEntry createOrUpdateScoreEntry(Dog dog, LocalDate date, int score) {
        Optional<ScoreEntry> optional = scoreEntryRepo.findByDogAndDate(dog, date);
        ScoreEntry entry = optional.orElse(new ScoreEntry(dog, date, score));
        entry.setScore(score);
        return scoreEntryRepo.save(entry);
    }

    // Pobiera punktacje dla psa w danym okresie
    public List<ScoreEntry> getScoresForDogBetween(Dog dog, LocalDate from, LocalDate to) {
        return scoreEntryRepo.findByDogAndDateBetween(dog, from, to);
    }

    // Pobiera punktację dla psa w danym dniu
    public Optional<ScoreEntry> getScoreForDogAndDate(Dog dog, LocalDate date) {
        return scoreEntryRepo.findByDogAndDate(dog, date);
    }
    public List<ScoreEntry> getAllScoresForDog(Dog dog) {
        return scoreEntryRepository.findAllByDog(dog);
    }
}
