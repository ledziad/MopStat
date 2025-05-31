package com.mopstat.mopstat.service;

import com.mopstat.mopstat.dto.DogDTO;
import com.mopstat.mopstat.model.Dog;
import com.mopstat.mopstat.model.User;
import com.mopstat.mopstat.repository.DogRepository;
import com.mopstat.mopstat.repository.UserRepository;
import com.mopstat.mopstat.repository.DailyRecordRepository;
import com.mopstat.mopstat.repository.ScoreEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DogService {
    private final DogRepository dogRepository;
    private final UserRepository userRepository;
    private final DailyRecordRepository dailyRecordRepository;
    private final ScoreEntryRepository scoreEntryRepository;

    @Autowired
    public DogService(
            DogRepository dogRepository,
            UserRepository userRepository,
            DailyRecordRepository dailyRecordRepository,
            ScoreEntryRepository scoreEntryRepository
    ) {
        this.dogRepository = dogRepository;
        this.userRepository = userRepository;
        this.dailyRecordRepository = dailyRecordRepository;
        this.scoreEntryRepository = scoreEntryRepository;
    }

    // Pobiera psy tylko aktualnego usera!
    public List<DogDTO> getAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return dogRepository.findByUserId(user.getId()).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    public List<DogDTO> getDogsForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return dogRepository.findByUserId(user.getId()).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    // Tworzy psa, przypisując go do aktualnego usera!
    public DogDTO create(DogDTO dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (dogRepository.existsByUserIdAndName(user.getId(), dto.getName())) {
            throw new IllegalArgumentException("Masz już psa o imieniu '" + dto.getName() + "'!");
        }
        Dog dog = new Dog(
                dto.getName(),
                dto.getPersonality(),
                dto.getImagePath(),
                user // nowy pies jest przypisany do usera!
        );
        Dog saved = dogRepository.save(dog);
        return toDTO(saved);
    }
    public DogDTO update(Long id, DogDTO dto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Dog dog = dogRepository.findById(id)
                .filter(d -> d.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Nie masz dostępu do tego psa!"));

        // Unikalność imienia dla usera!
        if (!dog.getName().equals(dto.getName()) &&
                dogRepository.existsByUserIdAndName(user.getId(), dto.getName())) {
            throw new IllegalArgumentException("Masz już psa o imieniu '" + dto.getName() + "'!");
        }

        dog.setName(dto.getName());
        dog.setPersonality(dto.getPersonality());
        dog.setImagePath(dto.getImagePath());
        dogRepository.save(dog);
        return toDTO(dog);
    }


    public DogDTO getById(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Dog dog = dogRepository.findById(id)
                .filter(d -> d.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Nie masz dostępu do tego psa!"));
        return toDTO(dog);
    }

    @Transactional
    public void deleteDogById(Long id, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Dog dog = dogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dog not found"));
        // Sprawdź, czy pies należy do tego usera!
        if (!dog.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Nie masz dostępu do tego psa!");
        }

        // Najpierw usuń punkty
        scoreEntryRepository.deleteAllByDogId(id);

        // Potem usuń wpisy dzienne
        dailyRecordRepository.deleteAllByDogId(id);

        // Na końcu usuń psa
        dogRepository.deleteById(id);
    }

    private DogDTO toDTO(Dog dog) {
        return new DogDTO(
                dog.getId(),
                dog.getName(),
                dog.getPersonality(),
                dog.getImagePath()
        );
    }
}
