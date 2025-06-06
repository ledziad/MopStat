package com.mopstat.mopstat.service;

import com.mopstat.mopstat.model.DailyRecord;
import com.mopstat.mopstat.model.Dog;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class ScoreEntryServiceTest {

    @Test
    void testCalculateScore() {
        ScoreEntryService service = new ScoreEntryService(null, null); // testujemy tylko logikę, repozytoria niepotrzebne

        DailyRecord record = new DailyRecord();
        record.setMeals(2);      // 2 x 2 = 4
        record.setPoops(1);      // 1 x 2 = 2
        record.setWalks(3);      // 3 x 3 = 9
        record.setMoodNote("Wesoły"); // +1

        int score = service.calculateScore(record);

        assertEquals(16, score); // 4+2+9+1 = 16
    }
}
