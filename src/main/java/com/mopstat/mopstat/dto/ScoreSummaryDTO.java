package com.mopstat.mopstat.dto;

import java.time.LocalDate;
import java.util.List;

public class ScoreSummaryDTO {
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

    private int totalScore;
    private List<DailyScoreDTO> dailyScores;

    public ScoreSummaryDTO(int totalScore, List<DailyScoreDTO> dailyScores) {
        this.totalScore = totalScore;
        this.dailyScores = dailyScores;
    }

    public int getTotalScore() { return totalScore; }
    public List<DailyScoreDTO> getDailyScores() { return dailyScores; }
}
