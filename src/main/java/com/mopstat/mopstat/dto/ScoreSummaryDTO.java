package com.mopstat.mopstat.dto;

import java.time.LocalDate;
import java.util.List;

public class ScoreSummaryDTO {
    // Punkty per dzień (np. do wykresu aktywności)
    public static class DailyScoreDTO {
        private LocalDate date;
        private int score;

        public DailyScoreDTO(LocalDate date, int score) {
            this.date = date;
            this.score = score;
        }
        public LocalDate getDate() { return date; }
        public int getScore() { return score; }
    }

    // NOWE: Punkty dla każdego psa (ranking, wykres słupkowy)
    public static class DogScoreDTO {
        private Long dogId;
        private String dogName;
        private int score;

        public DogScoreDTO(Long dogId, String dogName, int score) {
            this.dogId = dogId;
            this.dogName = dogName;
            this.score = score;
        }
        public Long getDogId() { return dogId; }
        public String getDogName() { return dogName; }
        public int getScore() { return score; }
    }

    private int totalScore;
    private List<DailyScoreDTO> dailyScores;
    private List<DogScoreDTO> dogScores; // <- nowa lista do rankingu

    // Nowy konstruktor (pełny)
    public ScoreSummaryDTO(int totalScore, List<DailyScoreDTO> dailyScores, List<DogScoreDTO> dogScores) {
        this.totalScore = totalScore;
        this.dailyScores = dailyScores;
        this.dogScores = dogScores;
    }

    // Wsteczna kompatybilność (konstruktor bez rankingu)
    public ScoreSummaryDTO(int totalScore, List<DailyScoreDTO> dailyScores) {
        this(totalScore, dailyScores, null);
    }

    public int getTotalScore() { return totalScore; }
    public List<DailyScoreDTO> getDailyScores() { return dailyScores; }
    public List<DogScoreDTO> getDogScores() { return dogScores; }
}
