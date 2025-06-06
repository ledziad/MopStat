package com.mopstat.mopstat.service;

import com.mopstat.mopstat.dto.ScoreSummaryDTO;
import com.mopstat.mopstat.model.Dog;
import com.mopstat.mopstat.model.ScoreEntry;
import com.mopstat.mopstat.repository.DogRepository;
import com.mopstat.mopstat.repository.ScoreEntryRepository;
import com.mopstat.mopstat.model.User;
import com.mopstat.mopstat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScoreSummaryService {

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private ScoreEntryRepository scoreEntryRepository;

    @Autowired
    private UserRepository userRepository;

    public ScoreSummaryDTO getUserScoreSummary(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        List<Dog> dogs = dogRepository.findByUserId(user.getId());
        List<ScoreEntry> entries = new ArrayList<>();
        Map<Long, Integer> perDog = new HashMap<>(); // sumy punktów dla każdego psa

        for (Dog dog : dogs) {
            List<ScoreEntry> dogEntries = scoreEntryRepository.findAllByDogId(dog.getId());
            entries.addAll(dogEntries);
            int dogSum = dogEntries.stream().mapToInt(ScoreEntry::getScore).sum();
            perDog.put(dog.getId(), dogSum);
        }

        int total = entries.stream().mapToInt(ScoreEntry::getScore).sum();
        Map<LocalDate, Integer> perDay = entries.stream()
                .collect(Collectors.groupingBy(
                        ScoreEntry::getDate, Collectors.summingInt(ScoreEntry::getScore)
                ));

        List<ScoreSummaryDTO.DailyScoreDTO> dailyScores = perDay.entrySet().stream()
                .map(e -> new ScoreSummaryDTO.DailyScoreDTO(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(ScoreSummaryDTO.DailyScoreDTO::getDate))
                .collect(Collectors.toList());

        // Lista sum punktów każdego psa (do rankingu)
        List<ScoreSummaryDTO.DogScoreDTO> dogScores = dogs.stream()
                .map(d -> new ScoreSummaryDTO.DogScoreDTO(d.getId(), d.getName(), perDog.getOrDefault(d.getId(), 0)))
                .sorted(Comparator.comparingInt(ScoreSummaryDTO.DogScoreDTO::getScore).reversed())
                .collect(Collectors.toList());

        // Zwracamy całość w jednym DTO
        return new ScoreSummaryDTO(total, dailyScores, dogScores);
    }
}
