package com.mopstat.mopstat.repository;

import com.mopstat.mopstat.model.ScoreEntry;
import com.mopstat.mopstat.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScoreEntryRepository extends JpaRepository<ScoreEntry, Long> {
    Optional<ScoreEntry> findByDogAndDate(Dog dog, LocalDate date);
    List<ScoreEntry> findByDogAndDateBetween(Dog dog, LocalDate from, LocalDate to);
    List<ScoreEntry> findAllByDogId(Long dogId);
    List<ScoreEntry> findAllByDog(Dog dog);
void deleteAllByDogId(Long dogId);
}

