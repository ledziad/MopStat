package com.mopstat.mopstat.controller;

import com.mopstat.mopstat.model.Dog;
import com.mopstat.mopstat.model.ScoreEntry;
import com.mopstat.mopstat.dto.ScoreEntryDTO;
import com.mopstat.mopstat.mapper.ScoreEntryMapper;
import com.mopstat.mopstat.repository.DogRepository;
import com.mopstat.mopstat.service.ScoreEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dogs/{dogId}/score")
public class ScoreEntryController {

    private final DogRepository dogRepo;
    private final ScoreEntryService scoreEntryService;

    @Autowired
    public ScoreEntryController(DogRepository dogRepo, ScoreEntryService scoreEntryService) {
        this.dogRepo = dogRepo;
        this.scoreEntryService = scoreEntryService;
    }

    // 1. Punkty za dzisiaj
    @GetMapping("/today")
    public ScoreEntryDTO getTodayScore(@PathVariable Long dogId) {
        Dog dog = dogRepo.findById(dogId).orElseThrow();
        LocalDate today = LocalDate.now();
        return scoreEntryService.getScoreForDogAndDate(dog, today)
                .map(ScoreEntryMapper::toDTO)
                .orElse(new ScoreEntryDTO(null, dogId, today, 0));
    }

    // 2. Lista scoreEntry w zadanym zakresie dat lub WSZYSTKIE (gdy brak from/to)
    @GetMapping
    public List<ScoreEntryDTO> getScores(
            @PathVariable Long dogId,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to
    ) {
        Dog dog = dogRepo.findById(dogId).orElseThrow();

        if (from != null && to != null) {
            // Score w zakresie dat
            return scoreEntryService.getScoresForDogBetween(dog, from, to)
                    .stream()
                    .map(ScoreEntryMapper::toDTO)
                    .collect(Collectors.toList());
        } else {
            // Score dla wszystkich wpisów
            return scoreEntryService.getAllScoresForDog(dog)
                    .stream()
                    .map(ScoreEntryMapper::toDTO)
                    .collect(Collectors.toList());
        }
    }

    // 3. Suma punktów psa (jedna liczba, np. do dashboardu/rankingu)
    @GetMapping("/sum")
    public int getTotalScore(@PathVariable Long dogId) {
        Dog dog = dogRepo.findById(dogId).orElseThrow();
        return scoreEntryService.getAllScoresForDog(dog)
                .stream()
                .mapToInt(ScoreEntry::getScore)
                .sum();
    }
}
