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

import java.security.Principal;
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
        for (Dog dog : dogs) {
            entries.addAll(scoreEntryRepository.findAllByDogId(dog.getId()));
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
        return new ScoreSummaryDTO(total, dailyScores);
    }
}
