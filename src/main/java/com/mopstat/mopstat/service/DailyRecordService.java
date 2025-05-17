package com.mopstat.mopstat.service;

import com.mopstat.mopstat.dto.DailyRecordDTO;
import com.mopstat.mopstat.model.DailyRecord;
import com.mopstat.mopstat.model.Dog;
import com.mopstat.mopstat.model.User;
import com.mopstat.mopstat.repository.DailyRecordRepository;
import com.mopstat.mopstat.repository.DogRepository;
import com.mopstat.mopstat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyRecordService {

    private final DailyRecordRepository dailyRecordRepository;
    private final DogRepository dogRepository;
    private final UserRepository userRepository;

    @Autowired
    public DailyRecordService(DailyRecordRepository dailyRecordRepository, DogRepository dogRepository, UserRepository userRepository) {
        this.dailyRecordRepository = dailyRecordRepository;
        this.dogRepository = dogRepository;
        this.userRepository = userRepository;
    }

    public List<DailyRecordDTO> getAllForCurrentUser() {
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<DailyRecord> records = dailyRecordRepository.findAllByDog_User_Id(user.getId());
        return records.stream().map(this::mapToDto).toList();
    }

    public DailyRecordDTO addRecord(DailyRecordDTO recordDTO) {
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Upewnij się, że pies należy do tego usera
        Dog dog = dogRepository.findById(recordDTO.getDogId())
                .filter(d -> d.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Nie masz dostępu do tego psa!"));

        DailyRecord record = new DailyRecord();
        record.setDog(dog);
        record.setDate(recordDTO.getDate());
        record.setMeals(recordDTO.getMeals());
        record.setPoops(recordDTO.getPoops());
        record.setWalks(recordDTO.getWalks());
        record.setMoodNote(recordDTO.getMoodNote());

        DailyRecord saved = dailyRecordRepository.save(record);
        return mapToDto(saved);
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private DailyRecordDTO mapToDto(DailyRecord record) {
        return new DailyRecordDTO(
                record.getId(),
                record.getDate(),
                record.getMeals(),
                record.getPoops(),
                record.getWalks(),
                record.getMoodNote(),
                record.getDog().getId() // dogId
        );
    }
}
