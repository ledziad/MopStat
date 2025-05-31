package com.mopstat.mopstat.service;

import com.mopstat.mopstat.dto.DailyRecordDTO;
import com.mopstat.mopstat.model.DailyRecord;
import com.mopstat.mopstat.model.Dog;
import com.mopstat.mopstat.model.User;
import com.mopstat.mopstat.repository.DailyRecordRepository;
import com.mopstat.mopstat.repository.DogRepository;
import com.mopstat.mopstat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DailyRecordService {
    private final ScoreEntryService scoreEntryService;
    private final DailyRecordRepository dailyRecordRepository;
    private final DogRepository dogRepository;
    private final UserRepository userRepository;

    @Autowired
    public DailyRecordService(DailyRecordRepository dailyRecordRepository, DogRepository dogRepository, UserRepository userRepository, ScoreEntryService scoreEntryService) {
        this.dailyRecordRepository = dailyRecordRepository;
        this.dogRepository = dogRepository;
        this.userRepository = userRepository;
        this.scoreEntryService = scoreEntryService;
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

        int score = scoreEntryService.calculateScore(saved);
        scoreEntryService.createOrUpdateScoreEntry(dog, saved.getDate(), score);

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
    public List<DailyRecordDTO> getRecordsForDog(Long dogId) {
        // Zabezpieczenie: pies musi należeć do zalogowanego usera!
        String username = getCurrentUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Dog dog = dogRepository.findById(dogId)
                .filter(d -> d.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Nie masz dostępu do tego psa!"));

        List<DailyRecord> records = dailyRecordRepository.findByDogId(dogId);
        return records.stream().map(this::mapToDto).toList();
    }
    public DailyRecord addDailyRecord(DailyRecord record) {
        DailyRecord savedRecord = dailyRecordRepository.save(record);

        int score = scoreEntryService.calculateScore(savedRecord);
        scoreEntryService.createOrUpdateScoreEntry(savedRecord.getDog(), savedRecord.getDate(), score);

        return savedRecord;
    }
    public void deleteRecord(Long dogId, Long recordId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Dog dog = dogRepository.findById(dogId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dog not found"));
        if (!dog.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this dog");
        }
        DailyRecord record = dailyRecordRepository.findById(recordId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found"));
        if (!record.getDog().getId().equals(dogId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Record does not belong to this dog");
        }
        dailyRecordRepository.deleteById(recordId);
    }
    public DailyRecordDTO editRecord(Long dogId, Long recordId, DailyRecordDTO dto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Dog dog = dogRepository.findById(dogId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dog not found"));
        if (!dog.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this dog");
        }
        DailyRecord record = dailyRecordRepository.findById(recordId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found"));
        if (!record.getDog().getId().equals(dogId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Record does not belong to this dog");
        }

        // Aktualizujemy dane rekordu
        record.setDate(dto.getDate());
        record.setMeals(dto.getMeals());
        record.setPoops(dto.getPoops());
        record.setWalks(dto.getWalks());
        record.setMoodNote(dto.getMoodNote());

        DailyRecord updated = dailyRecordRepository.save(record);

        // Zamień na DTO, jeśli masz mappera
        return new DailyRecordDTO(
                updated.getId(),
                updated.getDate(),
                updated.getMeals(),
                updated.getPoops(),
                updated.getWalks(),
                updated.getMoodNote(),
                updated.getDog().getId()
        );

    }

}
