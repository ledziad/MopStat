package com.mopstat.mopstat.service;

import com.mopstat.mopstat.dto.DailyRecordDTO;
import com.mopstat.mopstat.repository.DailyRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyRecordService {

    private final DailyRecordRepository dailyRecordRepository;

    @Autowired
    public DailyRecordService(DailyRecordRepository dailyRecordRepository) {
        this.dailyRecordRepository = dailyRecordRepository;
    }
    /*
    // Docelowa wersja: pobieranie z bazy (przez repozytorium)
    public List<DailyRecordDTO> getRecordsForDog(Long dogId) {
        return dailyRecordRepository.findByDogId(dogId)
                .stream()
                .map(record -> new DailyRecordDTO(
                        record.getId(),
                        record.getDate(),
                        record.getMeals(),
                        record.getPoops(),
                        record.getWalks(),
                        record.getMoodNote()
                ))
                .collect(Collectors.toList());
    }
    */
    // --- Jeśli chcesz mieć stub na czas developmentu/testów, możesz zakomentować powyższe

    public List<DailyRecordDTO> getRecordsForDog(Long dogId) {
        return List.of(
                new DailyRecordDTO(1L, java.time.LocalDate.now(), 2, 1, 2, "Wesoły"),
                new DailyRecordDTO(2L, java.time.LocalDate.now().minusDays(1), 1, 2, 1, "Lenistwo po spacerze")
        );
    }

}
